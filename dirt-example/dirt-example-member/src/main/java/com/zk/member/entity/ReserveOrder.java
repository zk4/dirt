package com.zk.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zk.dirt.annotation.DirtAction;
import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtField;
import com.zk.dirt.core.eDirtEntityRelation;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.entity.DirtBaseIdEntity;
import com.zk.dirt.intef.iListable;
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
    //
    @DirtField(title = "预约商品 id", idOfEntity = ReserveProduct.class)
    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "reserveProduct")

            ReserveProduct reserveProduct;

    //
    @DirtField(title = "预约商品2", idOfEntity = ReserveProduct.class)
    @ManyToOne(fetch = FetchType.LAZY)
    ReserveProduct reserveProduct2;

    @DirtField(title = "abc", idOfEntity = ReserveProduct.class)
    @ManyToOne(fetch = FetchType.LAZY)
    ReserveProduct hello;
    //@DirtField(title = "预约商品 id",
    //        idOfEntity = ReserveProduct.class,
    //        dirtSubmit = @DirtSubmit
    //)
    //@Column(name = "reserveProduct",insertable = false, updatable = false)
    //Long reserveProductId;


    @DirtField(title = "确认入场",  uiType = eUIType.switching)
    Boolean entered;

    //
    //@OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "member", insertable = false, updatable = false)
    //@JsonIgnore
    //Member member;
    //
    //@DirtField(title = "会员 id",
    //        idOfEntity = Member.class,
    //        relation = eDirtEntityRelation.OneToOne,
    //
    //        filters = true,
    //        onFilter = true,
    //        dirtSubmit = @DirtSubmit
    //)
    //@Column(name = "member")
    //Long memberId;

    @DirtAction(text = "详情", key = "detail")
    public void detail() {}

    @DirtAction(text = "删除", key = "delete")
    public void delete() {}

    @DirtAction(text = "编辑", key = "edit")
    public void edit() {}


    enum ePaytype  implements iListable {
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

        @DirtField(title = "支付方式",    uiType = eUIType.select)
        private ePaytype payType;//支付方式
        //无需提交需要购买的商品，去购物车再获取一遍
        //优惠，发票
        private String orderToken;//防重令牌
        private BigDecimal payPrice;//应付价格  验价
        private String note;//订单备注
    }


    @DirtAction(text = "下单", key = "makeOrder", confirm = true)
    public void makeOrder(OrderSubmitVo args) {
        System.out.println(args);
    }

}
