package com.zk.experiment;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtSnowIdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@DirtEntity("snow")
@DynamicUpdate
@DynamicInsert
@Table(name = "mms_snow")
@SQLDelete(sql = "UPDATE mms_snow SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = SnowDemo.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class SnowDemo extends DirtSnowIdEntity {


    @DirtField(title = "真实姓名",metable = true)
    @NotEmpty
    @Size(min = 2, max = 30)
    String name;

}
