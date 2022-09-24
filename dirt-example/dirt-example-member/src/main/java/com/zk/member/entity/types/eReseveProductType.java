package com.zk.member.entity.types;

import com.zk.dirt.intef.iEnumText;

public enum eReseveProductType implements iEnumText {
    STUDIO("剧场"),
    INDOOR("室内演出");

    private  String text;

    private eReseveProductType(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
