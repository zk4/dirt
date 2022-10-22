package com.zk.experiment.async;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class AsyncController {

    //@Autowired
    //private   TaskService taskService;
    //@Autowired
    //private   ExampleService exampleService;

    @Autowired
    private AsyncService asyncService;


    private Long sleepSecs = 1l;
    private Long sleepMillSecs = sleepSecs*1000;

    @RequestMapping(value = "/callable", method = RequestMethod.GET, produces = "application/json")
    public Callable<String> callable() {
        Callable<String> callable = asyncService::normal;
        return callable;
    }
    @RequestMapping(value = "/callable1", method = RequestMethod.GET, produces = "application/json")
    public Callable<String> callable1() {
        Callable<String> callable =()-> asyncService.normal();
        return callable;
    }

    @RequestMapping(value = "/callable2", method = RequestMethod.GET, produces = "application/json")
    public Callable<String> callable2() {
       return ()->{
           TimeUnit.SECONDS.sleep(sleepSecs);
           return Thread.currentThread().getName() + "callable2";
       };
    }


    @RequestMapping(value = "/method_pool_completefuture", method = RequestMethod.GET, produces = "application/json")
    public CompletableFuture<String> application_pool_completefuture() throws InterruptedException {
        return asyncService.completefuture();
    }

    @RequestMapping(value = "/deferred", method = RequestMethod.GET, produces = "application/json")
    public DeferredResult<String> deferred() {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        CompletableFuture.supplyAsync(asyncService::normal)
                .whenCompleteAsync((result, throwable) -> deferredResult.setResult(result));
        return deferredResult;
    }

    @GetMapping("/webAsyncTaskTimeout")
    public WebAsyncTask<String> timeoutWithWebAsyncTask() throws Exception {
        System.out.println(Thread.currentThread().getName() + " 主线程start");

        Callable<String> callable = () -> {
             TimeUnit.SECONDS.sleep(sleepSecs);
            return "webAsyncTaskTimeout";
        };

        // 采用WebAsyncTask 返回 这样可以处理超时和错误 同时也可以指定使用的Excutor名称
        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(sleepMillSecs, callable);
        // 注意：onCompletion表示完成，不管你是否超时、是否抛出异常，这个函数都会执行的
        webAsyncTask.onCompletion(() -> System.out.println("程序[正常执行]完成的回调"));

        // 这两个返回的内容，最终都会放进response里面去===========
        webAsyncTask.onTimeout(() -> "程序[超时]的回调");
        // 备注：这个是Spring5新增的
        webAsyncTask.onError(() -> "程序[出现异常]的回调");


        System.out.println(Thread.currentThread().getName() + " 主线程end");
        return webAsyncTask;
    }

    @GetMapping("nonblockRequest")
    public String nonblockRequest(){
        try {

            var response = asyncService.future();
            if(response.isDone()){
                System.out.println("method execute finished");
            } else {
                System.out.println(LocalDateTime.now() + " method execute not finished");
            }
            return "ok";
        } catch (Exception e) {
            return "nok";
        }
    }

}