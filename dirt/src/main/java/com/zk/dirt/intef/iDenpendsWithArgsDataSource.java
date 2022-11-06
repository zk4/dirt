package com.zk.dirt.intef;

import com.zk.dirt.core.Option;

import java.util.List;
import java.util.Map;

public interface iDenpendsWithArgsDataSource<K,V> extends  iDataSource{
    List<Option> getSource(String dependsEnityName,String dependsFiledName, Map<String, Object> args);
    K initialValue();
}
