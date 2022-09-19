package com.zk.dirt.core;

import lombok.Data;

@Data
public class DirtEnumValue<K,T> {
    private K text;
    private T status;
    private String color;
    //private List<DirtEnumValue> children;


    public DirtEnumValue(K text, T status, String color) {
        this.text = text;
        this.status = status;
        this.color = color;
    }


}