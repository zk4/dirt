package com.zk;

import com.zk.dirt.intef.iPersistProxy;
import com.zk.experiment.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("lazy 测试")
class LazyTest {


    @Autowired
    iPersistProxy persistProxy;


    @BeforeEach
    void setUp() {
        saveMember();
    }


    @Transactional
    void saveMember(){
        Member member = new Member();
        member.setName("刘正青");
        persistProxy.save(Member.class,member);

    }

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

    
}