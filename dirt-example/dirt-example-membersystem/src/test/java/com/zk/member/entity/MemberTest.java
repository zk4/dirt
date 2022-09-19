package com.zk.member.entity;

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