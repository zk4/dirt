package com.zk.dirt.split.intef;

import java.util.function.Function;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 请求拆分入口服务
 * @param <P> 入参
 * @param <R> 返回值
 */
public interface SplitService<P, R> {

    /**
     * 请求处理流程如下： <br />
     * 1. 对参数 P 进行拆分 <br />
     * 2. 用拆分结果分别调用 function 获取执行结果 <br />
     * 3. 将多个执行结果进行合并，并返回 <br />
     * @param function 执行方法，入参为 P，返回值为 R
     * @param p 调用函数入参
     * @param maxSize 每批次最大数量

     * @return
     */
     R split(Function<P, R> function, P p, int maxSize);
}
