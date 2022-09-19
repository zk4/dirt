package com.zk.config.redis;

public class CacheKeyMgr {

    public static String getLoginUser(String userId){
        return "LoginUser:"+userId;
    }
}
