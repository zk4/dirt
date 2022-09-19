package com.zk.member.entity.types;

import com.zk.dirt.intef.iListable;

public enum eStatus implements iListable {
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
