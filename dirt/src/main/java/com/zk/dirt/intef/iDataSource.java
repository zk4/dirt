package com.zk.dirt.intef;

import java.util.List;
import java.util.Map;

public interface iDataSource<K,V> {
    List<V> getSource(String dependsEnityName,String dependsFiledName, Map<String, Object> args);
    K initialValue();
}
