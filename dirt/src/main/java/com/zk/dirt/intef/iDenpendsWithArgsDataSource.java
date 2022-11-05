package com.zk.dirt.intef;

import com.zk.dirt.core.Option;

import java.util.List;
import java.util.Map;

public interface iDenpendsWithArgsDataSource<K,V> extends  iDataSource{
    List<Option> getSource( Map<String, Object> args);
    K initialValue();
}
