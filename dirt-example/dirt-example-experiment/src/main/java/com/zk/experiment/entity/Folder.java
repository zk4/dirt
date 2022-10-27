package com.zk.experiment.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;

@Getter
@Setter
@Entity(name="Folder")
@DirtEntity("文件夹")
@DynamicUpdate
@DynamicInsert
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Folder extends WindowFile {

    @DirtField
    private Integer fileCount;

    @DirtField(metable = true)
    private String color;


    //省略get set
}