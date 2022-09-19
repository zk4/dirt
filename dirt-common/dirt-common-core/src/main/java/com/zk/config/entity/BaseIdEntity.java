package com.zk.config.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLock;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
// 插入时只插入非 null, 子类如果需要也要加上
@DynamicInsert
// The dynamic update allows you to set just the columns that were modified in the associated entity. （包含设了 null) , 子类如果需要也要加
@DynamicUpdate
@MappedSuperclass

public class BaseIdEntity implements Serializable {

    private static final long serialVersionUID = 2359852974346431431L;

    @Id
    // 自增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @JsonIgnore
    @Column(nullable = false,columnDefinition="DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'")
    protected LocalDateTime updatedTime;


    @Column(nullable = false,columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    @JsonIgnore
    // 不触发乐观锁
    @OptimisticLock(excluded = true)
    protected LocalDateTime createdTime;


    // 软删除
    @JsonIgnore
    //@Column(nullable = false,columnDefinition="bit default false COMMENT '软删除状态, false: 未删除 true: 已删除'")
    protected Boolean  deleted;

    // 乐观锁
    // TODO: 与软删除冲突
    // 使用下面试试
    // @SQLDelete(sql="UPDATE service SET date_deletion=CURRENT_DATE WHERE id=? and version=? ")
    // @Where(clause="date_deletion IS NULL ")
    //@Version
    //private Integer version;


    //@PrePersist- 在新实体持久化之前（添加到EntityManager）
    //@PostPersist- 在数据库中存储新实体（在commit或期间flush）
    //@PostLoad - 从数据库中检索实体后。
    //@PreUpdate- 当一个实体被识别为被修改时EntityManager
    //@PostUpdate- 更新数据库中的实体（在commit或期间flush）
    //@PreRemove - 在EntityManager中标记要删除的实体时
    //@PostRemove- 从数据库中删除实体（在commit或期间flush）


    @PrePersist
    public void onCreate() {
        if(this.createdTime==null){
            this.createdTime = LocalDateTime.now();
        }
        if(this.updatedTime==null){
            this.updatedTime = LocalDateTime.now();
        }
        if(this.deleted==null){
            this.deleted = false;
        }

    }
    @PreUpdate
    public void onUpdate() {

        if(this.updatedTime==null){
            this.updatedTime = LocalDateTime.now();
        }
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseIdEntity)) {
            return false;
        }

        BaseIdEntity that = (BaseIdEntity) o;

        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }



}
