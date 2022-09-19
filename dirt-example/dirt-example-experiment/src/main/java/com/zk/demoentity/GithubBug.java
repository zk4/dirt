package com.zk.demoentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.*;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.experiment.eSubmitWidth;
import com.zk.member.provider.SQLProvider;
import com.zk.member.provider.StatusProvider;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
@DirtEntity
@DynamicUpdate
@DynamicInsert
// 让 jackson 忽略序列化 hibernateLazyInitializer handler 属性
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class GithubBug extends DirtBaseIdEntity {

    @DirtField(title = "bug",  dirtSubmit = @DirtSubmit(

    ))
    String bugName;


    @DirtField(
            title = "注释",

            uiType = eUIType.textarea,
            dirtSubmit =
            @DirtSubmit(
                    index = 1000,
                    placeholder = "写注释",
                    width = eSubmitWidth.LG
                    //rules = @DirtFomItemRule()
            )
    )
    String comments;

    @DirtField(title = "开关",  dirtSubmit = @DirtSubmit)
    Boolean click;

    @DirtField(title = "解决人",  dirtSubmit = @DirtSubmit)
    String resolver;

    @DirtField(title = "图片", uiType = eUIType.image)
    String imgUrl;

    //
    //@DirtField(title = "颜色", uiType = eDirtFormUIType.color)
    //String color;

    @DirtField(
            title = "多标签形式1",

            filters = true,
            onFilter = true,

            uiType = eUIType.checkbox,
            enumProvider = {StatusProvider.class},
            dirtSubmit = @DirtSubmit())
    @ElementCollection
    List<String> status;

    //
    //@DirtField(
    //        title = "多标签形式2",
    //
    //        filters = true,
    //        onFilter = true,
    //        uiType = eDirtFormUIType.checkbox,
    //        enumProvider = {StatusProvider.class},
    //        dirtSubmit = @DirtSubmit())
    //@Convert(converter= StringListConverter.class)
    //List<String> status2;

    @DirtField(
            title = "单标签",

            filters = true,
            onFilter = true,
            uiType = eUIType.treeSelect,
            enumProvider = {StatusProvider.class},
            dirtSubmit = @DirtSubmit())
    String singleStatus;


    @DirtField(title = "评分",
            sorter = true,
            uiType = eUIType.rate)
    Float rate;

    @DirtField(title = "进度", sorter = true, filters = true, uiType = eUIType.progress)
    Integer progress;


    // https://kostenko.org/blog/2020/10/jpa-manytoone-get-id-one-query.html
    // https://stackoverflow.com/questions/27930449/jpa-many-to-one-relation-need-to-save-only-id
    //---------------------------many to one 演示  ------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "githubRepo" )
    @DirtField(title = "仓库", sorter = true, filters = true, uiType = eUIType.select)
    GithubRepo githubRepo;


    //@DirtField(title = "仓库id",
    //        idOfEntity = GithubRepo.class,
    //
    //        filters = true,
    //        onFilter = true,
    //        //uiType = eDirtFormUIType.treeSelect,
    //        //enumProvider = {SQLProvider.class},
    //        dirtSubmit = @DirtSubmit
    //)
    //@Column(name = "githubRepo")
    //Long githubRepoId;

    @DirtField(title = "仓库名",

            filters = true,
            onFilter = true,
            uiType = eUIType.treeSelect,
            enumProvider = {SQLProvider.class},
            dirtSubmit = @DirtSubmit
    )
    String githubRepoName;

    //---------------------------many to one 演示 结束 ------------------------
    //
    //@OneToMany(fetch = FetchType.EAGER)
    //@JoinColumn(name = "students", insertable = false, updatable = false)
    //List<Student> students;

    //
    //@DirtField(title = "学生们",
    //        idOfEntity=Student.class,
    //        dirtSubmit = @DirtSubmit
    //)
    //@Column(name = "students")
    //List<Long> studentIds;

    //---------------------------many to one 演示  ------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car", insertable = false, updatable = false)
    @JsonIgnore
    Car car;


    @DirtField(title = "车 id",
            idOfEntity = Car.class,

            filters = true,
            onFilter = true,
            uiType = eUIType.select,
            sourceProvider = @DirtHQLSource(hql = "select new map( id as status,carName as text) from Car"),
            dirtSubmit = @DirtSubmit
    )
    @Column(name = "car")
    Long  carId;


    @DirtField(title = "localDate",

            uiType = eUIType.date,

            dirtSearch = @DirtSearch(
                    title = "时间范围",
                    valueType = eUIType.dateRange
            )
    )
    protected LocalDate localDate;

    @DirtField(title = "localDateTime",

            uiType = eUIType.dateTime,

            dirtSearch = @DirtSearch(
                    title = "时间范围",
                    valueType = eUIType.dateTimeRange
            )
    )
    protected LocalDateTime localDateTime;


    @DirtField(title = "localTime",


            uiType = eUIType.time,

            dirtSearch = @DirtSearch(
                    title = "时间范围",
                    valueType = eUIType.timeRange
            )
    )
    protected LocalTime localTime;



    @DirtField(title = "测试字段",  dirtSubmit = @DirtSubmit)
    Boolean aBoolean;


}
