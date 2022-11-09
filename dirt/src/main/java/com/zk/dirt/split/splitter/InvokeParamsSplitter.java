package com.zk.dirt.split.splitter;


import com.zk.dirt.split.invoker.InvokeParams;
import com.zk.dirt.split.intef.ParamSplitter;

import java.util.List;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 通用参数拆分器
 *
 */

public class InvokeParamsSplitter extends AbstractFixTypeParamSplitter<InvokeParams>{
    private final ParamSplitter paramSplitter;

    public InvokeParamsSplitter(ParamSplitter paramSplitter) {
        this.paramSplitter = paramSplitter;
    }

    @Override
    protected List<InvokeParams> doSplit(InvokeParams param, int maxSize) {
        return param.split(this.paramSplitter, maxSize);
    }
}
