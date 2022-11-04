package com.zk.upms;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtField;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.lang.reflect.ParameterizedType;

@Data
@DynamicInsert
@DynamicUpdate
@MappedSuperclass
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = MyBaseIdTreeEntity.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idNameObj")
public   class MyBaseIdTreeEntity<T> extends MyBaseIdEntity  {

    /**
     * 在 runtime 时拿到泛型的类型
     * @return
     */
    public String fetchGenericType(String T){
        if("T".equals(T)) {
            return ((Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0]).getClass().getSimpleName();
        }else{
            throw  new RuntimeException("不知道啥泛型");
        }
    }


    @Data
    @AllArgsConstructor
    public static class IdNameObj {
        Long id;
        String name;
        Boolean isLeaf;
    }

    @Transient
    IdNameObj idNameObj;

    public IdNameObj getIdNameObj() {
        return new IdNameObj(this.id,this.name,this.isLeaf);
    }

    Boolean isLeaf;



    @DirtField(title = "名字" )
    @NotEmpty
    @Size(max = 30)
    String name;



}
