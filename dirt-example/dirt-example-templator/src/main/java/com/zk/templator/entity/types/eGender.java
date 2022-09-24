package com.zk.templator.entity.types;

import com.zk.dirt.intef.iListable;

public enum  eGender  implements iListable {
    MALE("男"),
    FEMALE("女"),
    UNKOWN("未知");

    private  String text;

    private eGender(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
