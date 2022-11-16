package com.zk.intention.entity.root;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtSimpleIdEntity;
import com.zk.intention.entity.vo.Customer;
import com.zk.intention.entity.vo.Driver;
import com.zk.intention.entity.vo.Location;
import com.zk.intention.types.eDriverStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.EnumType.STRING;

@Getter
@Setter
//@Accessors(fluent = false, chain = true)
@Entity
@Table(name = "t_intention")
@DirtEntity(value = "乘客意向")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(scope = Intention.class,generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Intention  extends DirtSimpleIdEntity {
    @Embedded
    @DirtField(title = "起点")
    @AttributeOverrides({
            @AttributeOverride(name="longitude",column=@Column(name="startLongitude")),
            @AttributeOverride(name="latitude",column=@Column(name="startLatitude"))
    })
    Location startLocation;

    @Embedded
    @DirtField(title = "终点")
    @AttributeOverrides({
            @AttributeOverride(name="longitude",column=@Column(name="destLongitude")),
            @AttributeOverride(name="latitude",column=@Column(name="destLatitude"))
    })
    Location destLocation;

    @Embedded
    @DirtField(title = "乘客")
    private Customer customer;

    @Enumerated(value = STRING)
    @DirtField
    private eDriverStatus status;

    @Embedded
    @DirtField(title = "司机")
    private Driver selectedDriver;

    @DirtField(title = "候选")
    @OneToMany(fetch = FetchType.LAZY)
    // 允许只生成两张表的情况下，双向更新
    @JoinColumn(name = "intention_id")
    @JsonIdentityReference(alwaysAsId = true)

    private Set<Candidate> candidates  ;

    public boolean canMatchDriver() {
        if (status.equals(eDriverStatus.Inited)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean canConfirm() {
        if (status.equals(eDriverStatus.UnConfirmed)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean waitingConfirm() {
        if (canMatchDriver()) {
            this.status = eDriverStatus.UnConfirmed;
            return true;
        } else {
            return false;
        }
    }

    public boolean fail() {
        if (this.status == eDriverStatus.Inited) {
            this.status = eDriverStatus.Failed;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 抢单，先应答的司机抢单成功
     * 该方法线程不安全，请使用锁保证没有并发
     *
     * @param driverVo
     * @return 0 成功, -1 状态不对, -2 不是候选司机，-3 已被抢走
     */
    public void confirmIntention(Driver driverVo) {
        //判断状态
        if (!canConfirm()) {
            //状态不对
            //return -1;
            throw new RuntimeException("状态不对");
        }
        //判断是否是候选司机，不能随便什么司机都来抢单
        if (candidates.stream().map(Candidate::getId).noneMatch(id -> id == driverVo.getDriverId())) {
            throw new RuntimeException("不是候选司机");

            //return -2;
        }
        //将候选司机加入到列表中
        if (this.selectedDriver.getDriverId() == null) {
            this.selectedDriver = driverVo;
            this.status = eDriverStatus.Confirmed;
            //return 0;
        } else {
            throw new RuntimeException("selectedDriver is not null");
        }

    }
}
