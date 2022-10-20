package com.zk.experiment;

import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
@DynamicUpdate
@DynamicInsert
@DirtEntity("子文件夹")
public class Folder extends DirtBaseIdEntity {

    @OneToOne
    @DirtField
    @JoinColumn(name="fileId")
    WindowFile windowFile;


    @DirtField
    private Integer fileCount;

    @DirtField
    private String color;


    //省略get set
}