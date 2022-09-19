package com.zk.dirt.intef;

import java.util.Map;

public interface iEnumProvider<K,V> {
    Map<K, V> getSource();
    K initialValue();
}
