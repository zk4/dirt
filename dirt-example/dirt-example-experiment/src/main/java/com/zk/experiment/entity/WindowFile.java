package com.zk.experiment.entity;

import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.InheritanceType;

@Getter
@Setter
@Entity(name = "WindowFile")
@DirtEntity("基表")
@javax.persistence.Inheritance(strategy = InheritanceType.JOINED)
public class WindowFile  extends DirtBaseIdEntity {

    @DirtField
    public String parentName;

    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}


}