package com.zk.dirt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.eUIType;
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
    @DirtField(title = "id",index = -999999, uiType = eUIType.text,fixed = DirtField.eFixedType.LEFT,dirtSubmit = {})
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
    //@Column(nullable = false,columnDefinition="bit default false COMMENT '软删除状态, false: 未删除 true: 已删除'")
    protected Boolean  deleted;

    // 乐观锁
    //@Version
    //@Column(nullable = false,columnDefinition="int default 0 COMMENT '乐观锁'")
    //private Integer version;


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
}
