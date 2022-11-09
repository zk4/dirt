package com.zk.dirt.split.splitter;


import com.zk.dirt.split.intef.ParamSplitter;

import java.util.Collections;
import java.util.List;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 参数拆分器公共父类
 */
abstract class AbstractParamSplitter<P> implements ParamSplitter<P> {

    @Override
    public final List<P> split(P param, int maxSize) {
        if (param == null){
            return defaultValue();
        }
        return doSplit(param, maxSize);
    }

    protected abstract List<P> doSplit(P param, int maxSize);


    protected List<P> defaultValue() {
        return Collections.emptyList();
    }
}
