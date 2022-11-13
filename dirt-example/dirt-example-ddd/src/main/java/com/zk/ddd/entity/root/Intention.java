package com.zk.ddd.entity.root;

import com.zk.ddd.types.eStatus;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.entity.DirtSimpleIdEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.EnumType.STRING;

@Getter
@Setter
@Entity
@Table(name = "t_intention")
@DirtEntity(value = "意向")
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
    private eStatus status;

    @Embedded
    @DirtField(title = "司机")
    private Driver selectedDriver;

    @OneToMany
    // 允许只生成两张表的情况下，双向更新
    @JoinColumn(name = "itention_id")
    private Set<Candidate> candidates  ;

    public boolean canMatchDriver() {
        if (status.equals(eStatus.Inited)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean canConfirm() {
        if (status.equals(eStatus.UnConfirmed)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean waitingConfirm() {
        if (canMatchDriver()) {
            this.status = eStatus.UnConfirmed;
            return true;
        } else {
            return false;
        }
    }

    public boolean fail() {
        if (this.status == eStatus.Inited) {
            this.status = eStatus.Failed;
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
    public int confirmIntention(Driver driverVo) {
        //判断状态
        if (!canConfirm()) {
            //状态不对
            return -1;
        }
        //判断是否是候选司机，不能随便什么司机都来抢单
        if (candidates.stream().map(Candidate::getId).noneMatch(id -> id == driverVo.getDriverId())) {
            return -2;
        }
        //将候选司机加入到列表中
        if (this.selectedDriver == null) {
            this.selectedDriver = driverVo;
            this.status = eStatus.Confirmed;
            return 0;
        } else {
            return -3;
        }

    }
}
