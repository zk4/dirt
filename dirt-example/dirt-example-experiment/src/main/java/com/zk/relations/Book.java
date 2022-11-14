package com.zk.relations;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DirtEntity(value = "书")
@DynamicUpdate
@DynamicInsert
@Table(name = "t_book")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = Book.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")

 public class Book extends DirtBaseIdEntity {

     @DirtField(title = "名字")
     private String name;

    @DirtField(title = "书拥有者")
    @OneToOne
    @JsonIdentityReference(alwaysAsId = true)
    @JoinTable(name="t_user_book_rel",
            joinColumns={@JoinColumn(name="bookId")},
            inverseJoinColumns={@JoinColumn(name="studentId")})
    private Student student;
    ////////////////////////// Action //////////////////////////
    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}
}