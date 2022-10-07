package com.zk.experiment;

import com.fasterxml.jackson.annotation.*;
import com.zk.dirt.annotation.*;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;

@Getter
@Setter
@Entity
@DirtEntity("积分")
@DynamicUpdate
@DynamicInsert
@Table(name = "mms_integral")
@SQLDelete(sql = "UPDATE mms_integral SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = Integral.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class Integral extends DirtBaseIdEntity {


    @DirtField(title = "积分")
    Long total=0L;




    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "member", insertable = false, updatable = false)
    @JsonIgnore
    Member member;



    @DirtField(title = "会员id",
            idOfEntity = Member.class,
            dirtSubmit = @DirtSubmit
    )
    @Column(name = "member")
    Long memberId;


    //@DirtAction(text = "详情")
    //public void detail() {}

    //@DirtAction(text = "删除")
    //public void delete() {}

    @DirtAction(text = "编辑")
    public void edit() {}

    @Data
    @DirtEntity(visiable = false)
    static class Arg {
        @DirtField(title = "增加值")
        Long value;
    }

    @DirtAction(text = "增加积分")
    public void addIntegral(@DirtArg("args") Arg args) {
        if(this.total == null){
            this.total =0L;
        }
        this.total+=args.value;
    }

}
