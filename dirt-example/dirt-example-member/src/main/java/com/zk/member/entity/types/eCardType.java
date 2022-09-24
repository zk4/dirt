package com.zk.member.entity.types;

import com.zk.dirt.intef.iEnumText;

public enum eCardType implements iEnumText {
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
