package com.zk.dirt.split.merger;

import com.zk.dirt.split.intef.SmartResultMerger;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by taoli on 2022/7/9.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public class LongResultMerger
        extends AbstractResultMerger<Long>
        implements SmartResultMerger<Long> {
    @Override
    Long doMerge(List<Long> longs) {
        if (CollectionUtils.isEmpty(longs)){
            return 0L;
        }
        return longs.stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    @Override
    public boolean support(Class<Long> resultType) {
        return Long.class == resultType || Long.TYPE == resultType;
    }
}
