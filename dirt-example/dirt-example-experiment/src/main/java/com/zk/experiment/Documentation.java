package com.zk.experiment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.DirtField;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "documentation")
@SQLDelete(sql = "UPDATE documentation SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@DiscriminatorValue("documentation")
public class Documentation extends WindowFile {

    @DirtField
    public Integer size;
    //省略get set
}