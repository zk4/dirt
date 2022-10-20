package com.zk.dirt.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "WINDOW_FILE")
@DiscriminatorColumn(name = "DISCRIMINATOR", discriminatorType = DiscriminatorType.STRING, length = 30)
@DiscriminatorValue("WindowFile")
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@SQLDelete(sql = "UPDATE WindowFile SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class WindowFile  extends DirtBaseIdEntity {

    @DirtField
    public String parentName;

}