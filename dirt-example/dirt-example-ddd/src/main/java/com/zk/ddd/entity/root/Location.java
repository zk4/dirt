package com.zk.ddd.entity.root;

import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DirtEntity(visiable = false)
public class Location {
    @DirtField
    Double  longitude;
    @DirtField
    Double  latitude;
}
