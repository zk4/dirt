package com.zk.dirt.split.merger;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 *  List 结果合并器
 */
public class ListResultMerger
        extends AbstractFixTypeResultMerger<List> {
    @Override
    protected List defaultValue() {
        return Collections.emptyList();
    }

    @Override
    List doMerge(List<List> lists) {

        int size = lists.stream()
                .filter(Objects::nonNull)
                .mapToInt(List::size)
                .sum();
        if (size == 0){
            return defaultValue();
        }

        List result = Lists.newArrayListWithCapacity(size);
        for (List ds : lists){
            if (CollectionUtils.isNotEmpty(ds)){
                result.addAll(ds);
            }
        }
        return result;
    }
}
