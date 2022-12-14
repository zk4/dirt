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
        System.out.println(Thread.currentThread().getName() + " ?????????start");

        Callable<String> callable = () -> {
             TimeUnit.SECONDS.sleep(sleepSecs);
            return "webAsyncTaskTimeout";
        };

        // ??????WebAsyncTask ?????? ????????????????????????????????? ??????????????????????????????Excutor??????
        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(sleepMillSecs, callable);
        // ?????????onCompletion???????????????????????????????????????????????????????????????????????????????????????
        webAsyncTask.onCompletion(() -> System.out.println("??????[????????????]???????????????"));

        // ?????????????????????????????????????????????response?????????===========
        webAsyncTask.onTimeout(() -> "??????[??????]?????????");
        // ??????????????????Spring5?????????
        webAsyncTask.onError(() -> "??????[????????????]?????????");


        System.out.println(Thread.currentThread().getName() + " ?????????end");
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