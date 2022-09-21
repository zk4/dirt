package com.zk.member.entity;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.zk.dirt.core.DirtContext;
import com.zk.dirt.intef.iPersistProxy;
import com.zk.dirt.util.ArgsUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("lazy 测试")
@Rollback(value = false)
public class LazyTest {


    @Autowired
    iPersistProxy persistProxy;

    @Autowired
    EntityManager entityManager;

    @Autowired
    DirtContext dirtContext;


    @Test
    @DisplayName("getId() should not invoke select")
    void getId() {
        Member one = persistProxy.getOne(Member.class, 1L);
        System.out.println(one.getId());
    }

    @Test
    @DisplayName("getName() should invoke select")
    void getName() {
        Member one = persistProxy.getOne(Member.class, 1L);
        System.out.println(one.getName());
    }


    @Test
    @DisplayName("getItems()")
    void getItems() {
        Member one = persistProxy.getOne(Member.class, 1L);
        System.out.println(one.getItems());
    }

    @Test
    @Transactional
    @DisplayName("saveRelation#1()")
    void saveRelationWithReference() {
        Member one = persistProxy.getOne(Member.class, 1L);
        Item item = entityManager.getReference(Item.class, 4L);

        one.getItems().add(item);
        Member save = persistProxy.save(Member.class, one);
        System.out.println(save.getItems());
    }

    @Test
    @Transactional
    @DisplayName("saveRelation#2()")
    void saveRelationWithOnlyId() {
        Member one = persistProxy.getOne(Member.class, 1L);

        // convert id to reference entity if needed
        Long[] ids = {2L};
        Arrays.stream(ids).forEach(id ->
                {
                    Item reference = entityManager.getReference(Item.class, id);
                    one.getItems().add(reference);
                }
        );

        Member save = persistProxy.save(Member.class, one);
        System.out.println(save.getItems());
    }

    @Test
    @Transactional
    @DisplayName("saveRelation#3()")
    void saveRelationDeleteThenInsert() {
        Member one = persistProxy.getOne(Member.class, 1L);


        // convert id to reference entity if needed
        Long[] ids = {1L, 2L, 3L, 4L};
        Set<Item> collect = Arrays.stream(ids)
                .map(id -> entityManager.getReference(Item.class, id))
                .collect(Collectors.toSet());
        one.setItems(collect);

        Member save = persistProxy.save(Member.class, one);
        System.out.println(save.getItems());
    }

    @Test
    @Transactional
    @DisplayName("saveRelation#4()")
    void saveRelationForDirt() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, IntrospectionException {
        Member one = persistProxy.getOne(Member.class, 1L);

        // construct id args
        ArrayList<Long> ids = new ArrayList<>(Arrays.asList(1L, 2L, 3L, 4L));

        assignRelationIds(one, ids);


        Member save = persistProxy.save(Member.class, one);
        System.out.println(save.getItems());
    }

    /**
     *
     * @param one
     * @param ids
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void assignRelationIds(Member one, ArrayList<Long> ids) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        // convert ids to entity reference, then set it back to managed entity
        for (Field declaredField : Member.class.getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(OneToMany.class) || declaredField.isAnnotationPresent(ManyToMany.class)) {
                Class[] classes = ArgsUtil.getCollectionItemType(declaredField);
                Class innerType = classes[1];
                Set<Object> collect = ids.stream()
                        .map(id -> entityManager.getReference(innerType, id))
                        .collect(Collectors.toSet());

                // set entity reference back to oneToMany annotated field
                Method mehtod = new PropertyDescriptor(declaredField.getName(), Member.class).getWriteMethod();
                mehtod.invoke(one, collect);
            }
        }
    }

    @Test
    void genereateGetterName() {
        String item = BeanUtil.okNameForMutator(null, "item", true);
        System.out.println(item);

    }

    @BeforeEach
    void saveMember() {
        //Member member = new Member();
        //member.setName("from test");
        //member.setIdtype(eIdType.IDCARD);
        //member.setIdnumber("12345678901");
        //member.setNickname("nickname");
        //member.setGender(eGender.FEMALE);
        //member.setGender2(eGender.FEMALE);
        //member.setPhonenumber("12345678901");
        //Member one = persistProxy.save(Member.class, member);
        //System.out.println(one.getName());
    }

}