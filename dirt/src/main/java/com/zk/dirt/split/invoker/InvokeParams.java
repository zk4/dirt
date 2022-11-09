package com.zk.dirt.split.invoker;

import com.zk.dirt.split.intef.ParamSplitter;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 执行参数主要是对方法的入参进行封装
 *
 */

@Value
@Builder
public class InvokeParams {
    private final Object[] parameters;
    private final int splitParamIndex;

    public List<InvokeParams> split(ParamSplitter splitter, int maxSize){
        Object splitParamValue = parameters[this.splitParamIndex];
        List<Object> split = splitter.split(splitParamValue, maxSize);
        return split.stream()
                .map(this::newInvokeParams)
                .collect(Collectors.toList());
    }

    private InvokeParams newInvokeParams(Object param) {
        Object[] nParameters = new Object[parameters.length];
        System.arraycopy(parameters, 0, nParameters, 0, parameters.length);
        nParameters[splitParamIndex] = param;

        return InvokeParams.builder()
                .splitParamIndex(splitParamIndex)
                .parameters(nParameters)
                .build();
    }
}
