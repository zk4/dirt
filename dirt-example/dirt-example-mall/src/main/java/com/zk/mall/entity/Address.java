package com.zk.mall.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.eDirtViewType;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.intef.iEnumText;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Entity
@DirtEntity(value = "地址数据",viewType = eDirtViewType.Table)
@DynamicUpdate
@DynamicInsert
@Table(name = "mall_address")
@SQLDelete(sql = "UPDATE mall_address SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = Address.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class Address extends DirtBaseIdEntity {

    @DirtField(title = "名称" )
    @NotEmpty
    @Size(max = 30)
    String name;

    @ManyToOne
    @JsonIgnore
    Address parent;

    @DirtField(title = "子地址")
    @OneToMany
    @JoinColumn(name = "parent")
    Set<Address> subAddress;

    public enum eAddressType implements iEnumText {
        PROVINCE("省"),
        CITY("市"),
        ZONE("区"),
        STREET("街道");

        private  String text;

        eAddressType(String text) {
            this.text = text;
        }

        @Override
        public String getText() {
            return text;
        }
    }
    @DirtField(title = "类型" )
    eAddressType addressType;



////////////////////////// Action //////////////////////////
    @DirtAction(text = "详情")
    public void detail() {}

    @DirtAction(text = "删除")
    public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}


}
