package com.zk.dirt.split.invoker;


import com.zk.dirt.split.intef.SplitInvoker;
import com.zk.dirt.split.intef.SplitService;

import java.lang.reflect.Method;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 单参数 拆分执行器，主要用于对只有一个入参的方法进行封装
 */
public class SingleParamSplitInvoker
        extends AbstractSplitInvoker
        implements SplitInvoker {

    public SingleParamSplitInvoker(SplitService splitService,
                                   Method method,
                                   int sizePrePartition) {
        super(splitService, method, sizePrePartition);
    }

    @Override
    public Object invoke(Object target, Object[] args) {
        return this.getSplitService().split(param -> invokeMethod(target, param),
                args[0],
                getSizePrePartition());
    }

    private Object invokeMethod(Object target, Object param) {
        // 将拆分之后的参数进行封装，执行 method 方法
        Object[] paramToUse = new Object[]{param};
        try {
            return getMethod().invoke(target, paramToUse);
        } catch (Exception e) {
            if (e instanceof RuntimeException){
                throw (RuntimeException) e;
            }else {
                throw new RuntimeException(e);
            }
        }
    }
}
