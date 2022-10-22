package com.zk.experiment.async;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * Project : async-controller
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 02/09/18
 * Time: 13.39
 * To change this template use File | Settings | File Templates.
 */
@Service
@Slf4j
public class AsyncService {

    private Long sleepSecs =1l;
    private Long sleepMillSecs = sleepSecs*1000;

    public String normal() {
        try {
            TimeUnit.SECONDS.sleep(sleepSecs);
            return Thread.currentThread().getName()+"normal";
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    @Async
    public Future<String> future() {
        try {
            TimeUnit.SECONDS.sleep(sleepSecs);
            var response = new AsyncResult<String>(Thread.currentThread().getName()+"future");

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Async
    public Callable<String> callable() {
        return ()->{
            TimeUnit.SECONDS.sleep(sleepSecs);
            return Thread.currentThread().getName()+"callable";
        };
    }
    @Async
    public CompletableFuture<String> completefuture() throws InterruptedException {
        TimeUnit.SECONDS.sleep(sleepSecs);
        return CompletableFuture.completedFuture(Thread.currentThread().getName()+" completefuture");
    }



    @Async("threadPoolTaskExecutor")
    public Future<String> method_pool_future() {
        try {
            TimeUnit.SECONDS.sleep(sleepSecs);

            return new AsyncResult<String>(Thread.currentThread().getName()+"method_pool_future");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}