package com.zk.dirt.split.intef;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 智能结果合并器
 */
public interface SmartResultMerger<R> extends ResultMerger<R>{
    /**
     * 是否能支持特定结果的合并
     * @param resultType 结果类型
     * @return
     */
    boolean support(Class<R> resultType);
}
