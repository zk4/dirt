package com.zk.dirt.split.splitter;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zk.dirt.split.intef.SmartParamSplitter;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * Set 拆分器
 *
 */
public class SetParamSplitter
        extends AbstractFixTypeParamSplitter<Set>
        implements SmartParamSplitter<Set> {
    @Override
    protected List<Set> doSplit(Set param, int maxSize) {
        if (CollectionUtils.isEmpty(param)){
            return defaultValue();
        }

        List<Set> result = Lists.newArrayList();
        Set set = Sets.newHashSetWithExpectedSize(maxSize);
        result.add(set);
        for (Object o : param){
            set.add(o);
            if (set.size() == maxSize){
                set = Sets.newHashSetWithExpectedSize(maxSize);
                result.add(set);
            }
        }
        return result.stream()
                .filter(r -> CollectionUtils.isNotEmpty(r))
                .collect(Collectors.toList());
    }
}
