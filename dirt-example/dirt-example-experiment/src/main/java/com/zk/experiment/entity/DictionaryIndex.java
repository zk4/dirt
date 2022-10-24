package com.zk.experiment.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
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
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@DirtEntity("字典索引")
@DynamicUpdate
@DynamicInsert
@Table(name = "t_dictionary_index")
@SQLDelete(sql = "UPDATE t_dictionary_index SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class DictionaryIndex extends DirtBaseIdEntity {


    @DirtField(title = "字典名称")
    @NotEmpty
    @Column(nullable = false, columnDefinition = "varchar(100) DEFAULT '' COMMENT '字典名称'")
    String name;


    public enum eValueType implements iEnumText {

        NUMBER("数字"),
        STRING("字符串"),
        JSON_ARRAY("数组 JSON"),
        JSON_OBJECT("JSOn 对象");

        private  String text;

        eValueType(String text) {
            this.text = text;
        }

        @Override
        public String getText() {
            return this.text;
        }
    }

    @DirtField(title = "值类型")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    eValueType valueType;

    @DirtField(title = "字典 KV")
    @OneToMany
    // 允许只生成两张表的情况下，双向更新
    @JoinColumn(name = "dictionaryIndex")
    List<DictionaryEntry> entries;


    @DirtAction(text = "详情")
    public void detail() {
    }

    @DirtAction(text = "删除")
    public void delete() {
    }

    @DirtAction(text = "编辑")
    public void edit() {
    }


}
