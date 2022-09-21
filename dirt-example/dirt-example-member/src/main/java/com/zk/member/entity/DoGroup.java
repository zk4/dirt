package com.zk.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.annotation.DirtSearch;
import com.zk.dirt.core.eFilterOperator;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Getter
@Setter
@Entity
@DirtEntity
@DynamicUpdate
@DynamicInsert
@Table(name = "do_group")
@SQLDelete(sql = "UPDATE do_group SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@ToString
public class DoGroup extends DirtBaseIdEntity {
    @DirtField(title = "组名", dirtSearch = @DirtSearch(operator = eFilterOperator.LIKE))
    String name;
    @DirtField(title = "限制人数")
    @Min(1)
    @Max(500)
    Integer limitedSize;


    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JsonIgnore
    List<DoMemberGroupRelation> doMemberGroupRelations;
}
