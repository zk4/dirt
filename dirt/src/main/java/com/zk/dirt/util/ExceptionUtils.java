package com.zk.dirt.util;

public class ExceptionUtils {
    public static void conditionThrow(boolean b,String msg ){
        if(!b){
            throw new RuntimeException(msg);
        }
    }
}
