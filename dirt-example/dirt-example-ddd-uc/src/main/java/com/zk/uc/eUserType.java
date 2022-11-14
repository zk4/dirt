package com.zk.uc;

import com.zk.dirt.intef.iEnumText;

public enum eUserType implements iEnumText {
    Customer("消费者"), Driver("司机");

    String text;

    eUserType(String text) {
        this.text = text;
    }

    @Override
    public Object getText() {
        return text;
    }
}
