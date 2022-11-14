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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@DirtEntity(value = "学生")
@DynamicUpdate
@DynamicInsert
@Table(name = "t_Student")
@SQLDelete(sql = "UPDATE t_Student SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = Student.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")

 public class Student extends DirtBaseIdEntity {

     @DirtField(title = "名字",metable = true)
     private String name;

    @DirtField(title = "关系表",metable = true)
    @OneToMany
     // 允许只生成两张表的情况下，双向更新
     @JoinColumn(name = "student")
     @JsonIdentityReference(alwaysAsId = true)
     private Set<TeacherStudent> teacherStudents;

    @DirtField(title = "所有书")
    @OneToOne
    // 允许只生成两张表的情况下，双向更新
    // 指定 student_id，根据默认字段生成策略，下划线最好加上

    @JsonIdentityReference(alwaysAsId = true)
    @JoinTable(name="t_user_book_rel",
            joinColumns={@JoinColumn(name="studentId")},
            inverseJoinColumns={@JoinColumn(name="bookId")})
    private Book  books;
    ////////////////////////// Action //////////////////////////
    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}
}