package com.zk.experiment;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.intef.iDirtDictionaryEntryType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@DirtEntity("字典键值")
@DynamicUpdate
@DynamicInsert
@Table(name = " dictionary_entry")
@SQLDelete(sql = "UPDATE dictionary_entry SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@ToString
public class DictionaryEntry extends DirtBaseIdEntity implements iDirtDictionaryEntryType {

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
    @JoinColumn(name = "dictionaryIndex")
    // json 仅序列化为 id，避免循环
    @JsonIdentityReference(alwaysAsId = true)
    DictionaryIndex dictionaryIndex;

    @DirtAction(text = "详情", key = "detail")
    public void detail() {}

    @DirtAction(text = "删除", key = "delete")
    public void delete() {}

    @DirtAction(text = "编辑", key = "edit")
    public void edit() {}



}
