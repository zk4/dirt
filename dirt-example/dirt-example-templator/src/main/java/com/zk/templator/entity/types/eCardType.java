package com.zk.templator.entity.types;

import com.zk.dirt.intef.iListable;

public enum eCardType implements iListable {
    GROUP_CARD("团卡"),
    PERSONAL_CARD("个人卡");

    private  String text;

    private eCardType(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
