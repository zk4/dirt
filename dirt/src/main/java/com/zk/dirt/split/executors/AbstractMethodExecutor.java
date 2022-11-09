package com.zk.dirt.split.executors;


import com.zk.dirt.split.intef.MethodExecutor;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 抽象执行器，主要完成参数校验
 */
abstract class AbstractMethodExecutor implements MethodExecutor {

    @Override
    public final  <P, R> List<R> execute(Function<P, R> function, List<P> ps) {
        if (CollectionUtils.isEmpty(ps)){
            return Collections.emptyList();
        }
        return doExecute(function, ps);
    }

    /**
     * 子类扩展点
     * @param function
     * @param ps
     * @param <R>
     * @param <P>
     * @return
     */
    protected abstract <R, P> List<R> doExecute(Function<P, R> function, List<P> ps);
}
