package com.zk.dirt.split.executors;


import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zk.dirt.split.intef.MethodExecutor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;
/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 并行执行器，任务在线程池中并发执行
 */
public class ParallelMethodExecutor
        extends AbstractMethodExecutor
        implements MethodExecutor {
    // 线程池
    private final ExecutorService executor;
    // 每个线程的任务数
    private final int taskPreThread;

    public ParallelMethodExecutor(ExecutorService executor,
                                  int taskPreThread) {
        Preconditions.checkArgument(executor != null);
        Preconditions.checkArgument(taskPreThread > 0);

        this.executor = executor;
        this.taskPreThread = taskPreThread;
    }

    @Override
    protected <R, P> List<R> doExecute(Function<P, R> function, List<P> ps) {
        // 将拆分后的入参分为多组，并将其封装为 Task
        List<Task> tasks = Lists.partition(ps, this.taskPreThread).stream()
                .map(p -> new Task<>(function, p))
                .collect(Collectors.toList());

        // 只有一个任务，直接在调用线程中执行
        if (tasks.size() == 1){
            return tasks.get(0).call();
        }

        // 创建集合，用于收集 Future
        List<Future<List<R>>> futures = Lists.newArrayListWithCapacity(tasks.size() - 1);

        // 将任务提交至线程池中执行
        for (int i = 1; i< tasks.size();i ++){
            futures.add(this.executor.submit(tasks.get(i)));
        }


        List<List<R>> batchResult = Lists.newArrayListWithCapacity(tasks.size());

        // 第一个任务在调用线程中执行，避免调用线程等待，浪费资源
        List<R> task1Result = tasks.get(0).call();

        // 收集执行结果
        batchResult.add(task1Result);
        batchResult.addAll(getResultFromFuture(futures));

        // 计算结果总量
        int all = batchResult.stream()
                .mapToInt(List::size)
                .sum();
        List<R> result = Lists.newArrayListWithCapacity(all);

        // 获得所有执行结果
        for (List<R> rs : batchResult){
            result.addAll(rs);
        }

        return result;
    }

    /**
     * 从Future 中获取执行结果
     * @param futures
     * @param <R>
     * @return
     */
    private <R> Collection<? extends List<R>> getResultFromFuture(List<Future<List<R>>> futures) {
        return futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }

    /**
     * 线程池任务，用于封装对 params 的依次调用 <br />
     * 一个 Task 会执行多次函数调用，从而在并发度和资源量间做平衡
     * @param <P> 入参
     * @param <R> 返回值
     */
    static class Task<P, R> implements Callable<List<R>> {
        private final Function<P, R> function;
        private final List<P> params;

        Task(Function<P, R> function, List<P> params) {
            this.function = function;
            this.params = Collections.unmodifiableList(params);
        }

        @Override
        public List<R> call() {
            // 依次调用函数，并收集执行结果
            return this.params.stream()
                    .map(p -> function.apply(p))
                    .collect(Collectors.toList());
        }

    }
}
