package com.zk.dirt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtField;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Data

@DynamicInsert
@DynamicUpdate
@MappedSuperclass
public  class DirtSimpleIdEntity implements Serializable, iID {

    private static final long serialVersionUID = 2359852974346431431L;

    @Id
    @Access(AccessType.PROPERTY)

    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增
    @Column(name = "id")
    @DirtField(title = "id",index = -999999, fixed = DirtField.eFixedType.LEFT,dirtSubmit = {})
    protected Long id;

    @Data
    @AllArgsConstructor
    public static class IdObj {
        Long id;
    }

    @Transient
    IdObj idObj;

    public IdObj getIdObj() {
        return new IdObj(this.id);
    }


    // 软删除
    @JsonIgnore
    protected Boolean  deleted;


    @PrePersist
    public void onCreate() {

        if(this.deleted==null){
            this.deleted = false;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DirtSimpleIdEntity)) {
            return false;
        }

        DirtSimpleIdEntity that = (DirtSimpleIdEntity) o;

        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }



    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}

}
