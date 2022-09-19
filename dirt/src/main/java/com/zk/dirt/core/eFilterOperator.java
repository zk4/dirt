package com.zk.dirt.core;

// https://procomponents.ant.design/components/schema#valuetype
public enum eFilterOperator {
    EQUAL,
    // 这个没啥用，因为通过 eDirtFormUIType range 就能判定
    // BETWEEN,
    LE,
    LT,
    GE,
    GT,
    LIKE,
    LIKE_LEFT,
    LIKE_RIGHT,
    IN,

}