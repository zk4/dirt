package com.zk.ddd.entity.root;

import com.zk.dirt.intef.iEnumText;

public enum eGender implements iEnumText {
        MALE("男"),
        FEMALE("女");

        String text;

        eGender(String text) {
            this.text = text;
        }

        @Override
        public Object getText() {
            return text;
        }
    }