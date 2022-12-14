package com.zk.dirt.core;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirtEnumValue<K,T> {
    private K text;
    private T status;
    private String color;
    private  Integer sort;


    public DirtEnumValue(K text, T status) {
        this.text = text;
        this.status = status;
        this.color = "";
        this.sort = 0;
    }

    public DirtEnumValue(K text, T status, String color) {
        this.text = text;
        this.status = status;
        this.color = color;
        this.sort  = 0 ;
    }

    public DirtEnumValue(K text, T status, Integer sort) {
        this.text = text;
        this.status = status;
        this.sort = sort;
        this.color ="";
    }

    public DirtEnumValue(K text, T status, String color, Integer sort) {
        this.text = text;
        this.status = status;
        this.color = color;
        this.sort = sort;
    }
}