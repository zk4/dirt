package com.zk.dirt.split.merger;

import com.zk.dirt.split.intef.SmartResultMerger;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by taoli on 2022/7/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class IntResultMerger
        extends AbstractResultMerger<Integer>
        implements SmartResultMerger<Integer> {

    @Override
    public boolean support(Class<Integer> resultType) {
        return Integer.class == resultType || Integer.TYPE == resultType;
    }

    @Override
    Integer doMerge(List<Integer> integers) {
        if (CollectionUtils.isEmpty(integers)){
            return 0;
        }
        return integers.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
