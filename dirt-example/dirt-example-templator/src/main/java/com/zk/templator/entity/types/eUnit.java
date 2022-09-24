package com.zk.templator.entity.types;

import com.zk.dirt.intef.iListable;

public enum eUnit implements iListable {
    KG("千克"),
    G("克"),
    M("米"),
    M_SQUARE("平方米"),
    CM("厘米"),
    CM_SQUARE("平方厘米"),
    MM("毫米"),
    COUNT("数量");

    private  String text;

    private eUnit(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
