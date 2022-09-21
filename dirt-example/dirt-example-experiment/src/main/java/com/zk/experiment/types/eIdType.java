package com.zk.experiment.types;

import com.zk.dirt.intef.iListable;

public enum  eIdType implements iListable {
    MILITARY_CARD("军官证"),
    IDCARD("身份证");

    private  String text;

    private eIdType(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
