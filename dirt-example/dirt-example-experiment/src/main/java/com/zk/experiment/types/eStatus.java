package com.zk.experiment.types;

import com.zk.dirt.intef.iEnumText;

public enum eStatus implements iEnumText {
    ACTIVE("已启用"),
    DISALBE("已禁用");

    private  String text;

    private eStatus(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
