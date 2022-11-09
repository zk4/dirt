package com.zk.dirt.split.executors;

import com.google.common.base.Preconditions;
import com.zk.dirt.split.intef.MethodExecutor;
import com.zk.dirt.split.intef.ParamSplitter;
import com.zk.dirt.split.intef.ResultMerger;
import com.zk.dirt.split.intef.SplitService;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.function.Function;

/**
 * Created by taoli on 2022/7/8
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 将组件组合起来
 */
public class DefaultSplitService<P, R> implements SplitService<P, R> {
    private final ParamSplitter<P> paramSplitter;
    private final MethodExecutor methodExecutor;
    private final ResultMerger<R> resultMerger;
    public DefaultSplitService(ParamSplitter<P> paramSplitter,
                               MethodExecutor methodExecutor,
                               ResultMerger<R> resultMerger) {
        Preconditions.checkArgument(paramSplitter != null);
        Preconditions.checkArgument(methodExecutor != null);
        Preconditions.checkArgument(resultMerger != null);

        this.paramSplitter = paramSplitter;
        this.methodExecutor = methodExecutor;
        this.resultMerger = resultMerger;
    }


    @Override
    public R split(Function<P, R> function, P p, int maxSize) {
        Preconditions.checkArgument(function != null);
        Preconditions.checkArgument(maxSize > 0);

        // 入参为 null，直接调用函数
        if (p == null){
            return function.apply(p);
        }

        // 对参数进行拆分
        List<P> params = this.paramSplitter.split(p, maxSize);

        //没有拆分结果，直接调用函数
        if (CollectionUtils.isEmpty(params)){
            return function.apply(p);
        }

        // 拆分结果为 1，使用拆分直接调用函数
        if (params.size() == 1){
            return function.apply(params.get(0));
        }

        // 基于执行器 和 拆分结果 执行函数
        List<R> results = this.methodExecutor.execute(function, params);

        // 对执行结果进行合并处理
        R result = this.resultMerger.merge(results);

        return result;
    }
}
