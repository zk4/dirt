package com.zk.dirt.split.invoker;

import com.zk.dirt.split.intef.SplitInvoker;
import com.zk.dirt.split.intef.SplitService;
import lombok.Getter;

import java.lang.reflect.Method;
/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 */
@Getter
abstract class AbstractSplitInvoker implements SplitInvoker {
    private final SplitService splitService;
    private final Method method;
    private final int sizePrePartition;

    protected AbstractSplitInvoker(SplitService splitService, Method method, int sizePrePartition) {
        this.splitService = splitService;
        this.method = method;
        this.sizePrePartition = sizePrePartition;
    }

}
