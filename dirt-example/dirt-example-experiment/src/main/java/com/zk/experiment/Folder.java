package com.zk.experiment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "folder")
@SQLDelete(sql = "UPDATE folder SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@DiscriminatorValue("Folder")
public class Folder extends WindowFile {

    @DirtField
    private Integer fileCount;

    @DirtField
    private String color;


    //省略get set
}