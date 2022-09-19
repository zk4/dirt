package com.zk.utils;

import com.google.common.base.Strings;
import com.zk.config.entity.BaseIdEntity;
import org.apache.commons.collections4.ListUtils;

import java.util.List;
import java.util.stream.Collectors;

public class LogicUtils {
    static public  <T extends BaseIdEntity> void validteIds(List<T> dbEntitys, List<Long> sourceIds) {
        if(sourceIds == null){
            if(dbEntitys == null)
                return;
        }
        List<Long> dbIds = dbEntitys.stream().map(BaseIdEntity::getId).collect(Collectors.toList());
        List<Long> nonExistIds = ListUtils.removeAll(sourceIds, dbIds);
        if (nonExistIds.size() > 0) {
            String msg = Strings.lenientFormat("id %s %s", nonExistIds, "不存在");
            throw new RuntimeException(msg);
        }
    }
}
