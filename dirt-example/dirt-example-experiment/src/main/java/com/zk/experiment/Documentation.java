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
import javax.persistence.Table;

@Getter
@Setter
@Entity
@DynamicUpdate
@DynamicInsert
@DirtEntity("子文档")
@Table(name = "documentation")
public class Documentation extends DirtBaseIdEntity {


    @OneToOne
    @DirtField
    @JoinColumn(name="fileId")
    WindowFile windowFile;

    @DirtField
    public Integer size;
    //省略get set
}