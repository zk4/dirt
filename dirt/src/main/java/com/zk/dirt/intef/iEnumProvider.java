package com.zk.dirt.intef;

import java.util.Map;

public interface iEnumProvider<K,V> extends  iDataSource{
    Map<K, V> getSource();
    K initialValue();
}
