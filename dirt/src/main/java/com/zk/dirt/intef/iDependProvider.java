package com.zk.dirt.intef;

import java.util.Map;

public interface iDependProvider<K,V> {
    Map<K, V> getSource(String name);
    K initialValue();
}
