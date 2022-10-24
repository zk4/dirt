package com.zk.config.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zk.config.Snowflake;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
// 插入时只插入非 null, 子类如果需要也要加上
@DynamicInsert
// The dynamic update allows you to set just the columns that were modified in the associated entity. （包含设了 null) , 子类如果需要也要加
@DynamicUpdate
@MappedSuperclass
public class BaseSnowIdEntity implements Serializable {


    private static final long serialVersionUID = -415565197290161889L;
    // id 使用 snowflake, 见 @PrePersist
    @Id
    @Column(name = "id", nullable = false)
    protected Long id;

    @JsonIgnore
    @Column(columnDefinition="DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'")
    protected LocalDateTime updatedTime;


    @Column(columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    @JsonIgnore
    protected LocalDateTime createdTime;


    // 软删除
    @JsonIgnore
    @Column(columnDefinition=" bit default false COMMENT '软删除状态, 0: 未删除 1: 已删除'")
    protected Boolean  deleted;

    // 乐观锁
    //@Version
    //@Column(columnDefinition=" int default 0 COMMENT '乐观锁'")
    //private Integer version;

    @Size(max = 32)
    @Column(name = "tenant_id",columnDefinition=" char(32)  COMMENT '租户 id'")
    private String tenantId;


    static Snowflake snowflake = new Snowflake();
    @PrePersist
    public void onCreate() {
        if(this.id==null){
            this.id = snowflake.nextId();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseSnowIdEntity)) return false;

        BaseSnowIdEntity that = (BaseSnowIdEntity) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
