package com.zk.dirt.intef;

import java.util.Map;

public interface iDependProvider<K,V> {
    Map<K, V> getSource(Map<String,Object> args);
    K initialValue();
}
