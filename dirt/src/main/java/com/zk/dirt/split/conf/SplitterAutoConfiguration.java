package com.zk.dirt.split.conf;

import com.zk.dirt.split.SplitInvokerRegistry;
import com.zk.dirt.split.advice.SplitInterceptor;
import com.zk.dirt.split.annotation.Split;
import com.zk.dirt.split.beanpostprocessor.SplitInvokerProcessor;
import com.zk.dirt.split.executors.ParallelMethodExecutor;
import com.zk.dirt.split.intef.ParamSplitter;
import com.zk.dirt.split.intef.SmartParamSplitter;
import com.zk.dirt.split.merger.IntResultMerger;
import com.zk.dirt.split.merger.ListResultMerger;
import com.zk.dirt.split.merger.LongResultMerger;
import com.zk.dirt.split.merger.SetResultMerger;
import com.zk.dirt.split.splitter.AnnBasedParamSplitterBuilder;
import com.zk.dirt.split.splitter.ListParamSplitter;
import com.zk.dirt.split.splitter.SetParamSplitter;
import com.zk.dirt.split.splitter.SplittableParamSplitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * AutoConfiguration 自动配置主要完成：<br />
 * 1. PointcutAdvisor, 拦截器配置
 * 2. BeanProcessor，处理器配置，Spring 启动时，对 @Split 注解进行处理
 * 3. SplitInvokerRegistry，InvokerRegistry 使用单例特性，共享注册信息
 * 4. ParallelMethodExecutor，多线程执行器
 * 5. ParamSplitter，预制参数拆分器
 * 6. ResultMerger，预制结果合并器
 */

@Configuration
@Slf4j
public class SplitterAutoConfiguration {

    @Bean
    public PointcutAdvisor pointcutAdvisor(@Autowired SplitInterceptor splitInterceptor){
        return new DefaultPointcutAdvisor(new AnnotationMatchingPointcut(null, Split.class), splitInterceptor);
    }

    @Bean
    public SplitInvokerProcessor splitInvokerProcessor(SplitInvokerRegistry splitInvokerRegistry){
        return new SplitInvokerProcessor(splitInvokerRegistry);
    }

    @Bean
    @ConditionalOnMissingBean
    public SplitInterceptor splitInterceptor(SplitInvokerRegistry splitInvokerRegistry){
        return new SplitInterceptor(splitInvokerRegistry);
    }


    @Bean
    public SplitInvokerRegistry splitInvokerRegistry(){
        return new SplitInvokerRegistry();
    }

    @Bean
    @ConditionalOnMissingBean
    public ParallelMethodExecutor defaultSplitExecutor(){
        return new ParallelMethodExecutor(createExecutor(), 2);
    }

    private ExecutorService createExecutor() {
        int cpu = Runtime.getRuntime().availableProcessors();
        int nThreads = cpu * 100;
        BasicThreadFactory basicThreadFactory = new BasicThreadFactory.Builder()
                .namingPattern("Default-Split-Executor-Thread-%d")
                .daemon(true)
                .uncaughtExceptionHandler((thread, throwable) -> log.error("failed to run task on thread {}", thread, throwable)).build();

        return new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(),
                basicThreadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    @Bean
    public ListResultMerger listResultMerger(){
        return new ListResultMerger();
    }

    @Bean
    public LongResultMerger longResultMerger(){
        return new LongResultMerger();
    }

    @Bean
    public IntResultMerger intResultMerger(){
        return new IntResultMerger();
    }

    @Bean
    public SetResultMerger setResultMerger(){
        return new SetResultMerger();
    }

    @Bean
    public ParamSplitter listParamSplitter(){
        return new ListParamSplitter();
    }

    @Bean
    public ParamSplitter setParamSplitter(){
        return new SetParamSplitter();
    }

    @Bean
    public AnnBasedParamSplitterBuilder annBasedParamSplitterBuilder(List<SmartParamSplitter> smartParamSplitters){
        return new AnnBasedParamSplitterBuilder(smartParamSplitters);
    }

    @Bean
    public SplittableParamSplitter splittableParamSplitter(){
        return new SplittableParamSplitter();
    }
}
