package com.zk.experiment.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtBaseIdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@DirtEntity("核销历史")
@DynamicUpdate
@DynamicInsert
@Table(name = "mms_verification_history")
@SQLDelete(sql = "UPDATE mms_verification_history SET deleted = true WHERE id=?  and version=? ")
@Where(clause = "deleted=false")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = VerificationHistory.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "idObj")
public class VerificationHistory extends DirtBaseIdEntity {

    @DirtField(title = "用户")
    @ManyToOne
    @JoinColumn(name = "memberId")
    @JsonIdentityReference(alwaysAsId = true)
    Member member;

    @DirtField(title = "权益")
    @ManyToOne
    @JoinColumn(name = "benefitId")
    @JsonIdentityReference(alwaysAsId = true)
    Benefit benefit;

    @DirtAction(text = "详情")
    public void detail() {}

}
