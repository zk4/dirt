package com.zk.ddd.types;

import com.zk.dirt.intef.iEnumText;

public enum eStatus implements iEnumText {


    Inited("已创建"), UnConfirmed("未确认"), Confirmed("已确认"), Failed("失败");

    String text;

    eStatus(String text) {
        this.text = text;
    }

    @Override
    public Object getText() {
        return text;
    }
}
