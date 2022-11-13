package com.zk.ddd.entity.root;

import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;


@Embeddable
@Setter
@Getter
@DirtEntity(visiable = false)
public class Driver  {
    @DirtField(title = "司机 id")
    private Long driverId;
    @DirtField(title = "用户名")
    private String userName;
    @DirtField(title = "手机")
    private String mobile;
    @DirtField(title = "类型")
    private String type;
}
