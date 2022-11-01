package com.zk.templator.provider;

import com.google.common.collect.ImmutableMap;
import com.zk.dirt.core.DirtEnumValue;
import com.zk.dirt.intef.iNoArgDatasource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class StatusProvider implements iNoArgDatasource<String, DirtEnumValue> {
    @Override
    public ImmutableMap<String, DirtEnumValue> getSource() {
        List<DirtEnumValue> dirtEnumValues = Arrays.asList(new DirtEnumValue("全部", "", "white"));
        return ImmutableMap.of(
                "", new DirtEnumValue("全部", "", "white"),
                "close", new DirtEnumValue("关闭", "close", "gray"),
                "running", new DirtEnumValue("运行中", "running", "blue"),
                "online", new DirtEnumValue("已上线", "online", "green"),
                "error", new DirtEnumValue("异常", "error", "red")
        );
    }

    @Override
    public String initialValue() {
        return "";
    }
}
