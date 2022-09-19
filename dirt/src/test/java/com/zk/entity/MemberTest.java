package com.zk.entity;

import com.zk.member.entity.Member;
import com.zk.member.entity.ReserveProduct;
import org.junit.jupiter.api.Test;



class MemberTest {

    @Test
    void createOrder() {
        Member member = new Member();
        ReserveProduct reserveProduct = new ReserveProduct();
        reserveProduct.setCandidates(0);
        //member.createOrder(reserveProduct);
    }
}