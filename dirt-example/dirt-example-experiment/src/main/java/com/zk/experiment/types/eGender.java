package com.zk.experiment.types;

import com.zk.dirt.intef.iEnumText;

public enum  eGender  implements iEnumText {
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
