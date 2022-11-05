package com.zk.dirt.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DirtActionType {
    @JsonIgnore
    Method  method;
    String  text;
    String  desc;
    String  key;

    Map<String,List<DirtFieldType>> argColumnsMap= new LinkedHashMap<>();
}
