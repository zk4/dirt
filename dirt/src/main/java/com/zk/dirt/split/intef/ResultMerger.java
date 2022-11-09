package com.zk.dirt.split.intef;

import java.util.List;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 结果合并器，对执行结果进行合并处理
 **/
public interface ResultMerger<R> {

    /**
     * 对多个执行结果进行合并处理
     * @param rs 执行结果
     * @return 合并之后的最终结果
     */
    R merge(List<R> rs);
}
