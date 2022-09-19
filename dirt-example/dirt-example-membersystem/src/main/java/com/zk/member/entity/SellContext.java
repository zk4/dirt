package com.zk.member.entity;

import com.zk.dirt.annotation.DirtEntity;
import com.zk.dirt.annotation.DirtAction;


@DirtEntity
public class SellContext {

    @DirtAction(text = "帮用户下单", key = "sell")
    public void sell(Member member) {

    }

}
