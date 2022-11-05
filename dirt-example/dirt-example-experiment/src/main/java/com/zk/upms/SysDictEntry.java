package com.zk.upms;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.intef.iDirtDictionaryEntryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@DirtEntity(value = "字典键值",visiable = false)
@DynamicUpdate
@DynamicInsert
@Table(name = "sys_dict_entry")
//@SQLDelete(sql = "UPDATE sys_dict_entry SET deleted = true WHERE id=?  and version=? ")
//@Where(clause = "deleted=false")
@JsonIdentityInfo(scope = SysDictEntry.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class SysDictEntry extends DirtBaseIdEntity implements iDirtDictionaryEntryType {

    @DirtField(title = "字典排序")
    @Column(columnDefinition="int(4) DEFAULT 0 COMMENT '字典排序'")
    Integer dictSort=0;


    @DirtField(title = "字典 key")
    @NotEmpty
    @Column(columnDefinition="varchar(100) DEFAULT '' COMMENT '字典 key'")
    String dictKey;

    @DirtField(title = "字典 value")
    @NotEmpty
    @Column(columnDefinition="varchar(100) DEFAULT '' COMMENT '字典 value'")
    String dictValue;


    @DirtField(title = "字典索引")

    @ManyToOne
    // 允许只生成两张表的情况下，双向更新
    // @JoinColumn(name = "dictionaryIndex")
    // json 仅序列化为 id，避免循环
    @JsonIdentityReference(alwaysAsId = true)
    SysDict dictionaryIndex;







}
