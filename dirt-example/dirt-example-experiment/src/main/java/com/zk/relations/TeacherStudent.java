package com.zk.relations;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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


@Getter
@Setter
@Entity
@DirtEntity(value = "学生老师关系")
@DynamicUpdate
@DynamicInsert
@Table(name = "t_TeacherStudent",uniqueConstraints = @UniqueConstraint(columnNames = {"teacher", "student","deleted"}))
@SQLDelete(sql = "UPDATE t_TeacherStudent SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = TeacherStudent.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class TeacherStudent extends DirtBaseIdEntity {

    @DirtField(title = "老师 id",metable = true)
    @ManyToOne
    @JoinColumn(name="teacher")
    private Teacher teacher;

    @DirtField(title = "学生 id",metable = true)
    @ManyToOne
    @JoinColumn(name="student")
    private Student student;

    @DirtField(title = "印象分")
    @JoinColumn()
    @Column(nullable=true,name="impression_score")
    private int impressionScore;

    ////////////////////////// Action //////////////////////////
    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}
}