package com.zk.experiment.types;

import com.zk.dirt.intef.iEnumText;

public enum eWeekDay implements iEnumText {
    MONDAY("星期一"),
    TUESDAY("星期二"),
    WENSDAY("星期三"),
    THURSDAY("星期四"),
    FRIDAY("星期五"),
    SATURDAY("星期六"),
    SUNDAY("星期天");


    private  String text;

    private eWeekDay(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
