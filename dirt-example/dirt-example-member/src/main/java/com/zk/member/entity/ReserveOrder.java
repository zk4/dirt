package com.zk.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.eDirtEntityRelation;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.intef.iEnumText;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@DirtEntity
@DynamicUpdate
@DynamicInsert
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class ReserveOrder extends DirtBaseIdEntity {

    @DirtField(title = "预约商品 id")
    @ManyToOne(fetch = FetchType.LAZY)
    ReserveProduct reserveProduct;


    @DirtField(title = "预约商品2")
    @ManyToOne(fetch = FetchType.LAZY)
    ReserveProduct reserveProduct2;

    @DirtField
    @ManyToOne(fetch = FetchType.LAZY)
    ReserveProduct hello;

    @DirtField(title = "确认入场", uiType = eUIType.switching)
    Boolean entered;



    @DirtAction(text = "详情")
    public void detail() {
    }

    @DirtAction(text = "删除")
    public void delete() {
    }

    @DirtAction(text = "编辑")
    public void edit() {
    }


    enum ePaytype implements iEnumText {
        CASH,
        APLIPAY;

        @Override
        public String getText() {
            return this.toString();
        }
    }

    @Data
    @DirtEntity
    static class OrderSubmitVo {
        @DirtField(title = "会员 id",
                idOfEntity = Member.class,
                relation = eDirtEntityRelation.ManyToOne

        )
        @Column(name = "member")
        private Long addrId;//收货地址的id

        @DirtField(title = "支付方式", uiType = eUIType.select)
        private ePaytype payType;//支付方式
        //无需提交需要购买的商品，去购物车再获取一遍
        //优惠，发票
        private String orderToken;//防重令牌
        private BigDecimal payPrice;//应付价格  验价
        private String note;//订单备注
    }


    @DirtAction(text = "下单", key = "makeOrder")
    public void makeOrder(OrderSubmitVo arggggg) {
        System.out.println(arggggg);
    }

}
