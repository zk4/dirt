package com.zk.config.entity;


import com.zk.config.tenant.TenantAware;
import com.zk.config.tenant.TenantListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

/**
 * deprecated: 直接在BaseIdEntity, BaseSnowIdEntity 里继成
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@FilterDef(name = "tenantFilter", parameters = {@ParamDef(name = "tenantId", type = "string")})
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
@EntityListeners(TenantListener.class)
public abstract class BaseTenantIdEntity extends BaseIdEntity implements TenantAware {

    @Size(max = 32)
    @Column(name = "tenant_id",columnDefinition=" char(32)  COMMENT '租户 id'")
    private String tenantId;

    public BaseTenantIdEntity(String tenantId) {
        this.tenantId = tenantId;
    }


}