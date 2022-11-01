package com.zk.dirt.intef;

import java.util.Map;

public interface iWithArgDataSource<K,V> extends  iDataSource{
    Map<K, V> getSource(Map<String,Object> args);
    K initialValue();
}
