package com.zk;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = ExperimentApplication.class)
public class ControllerNetworkTest {

    @LocalServerPort
    int randomServerPort;


    @Test
    void createTest() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://127.0.0.1:"+randomServerPort+"/dirt/create?entityName=com.zk.experiment.Member")
                .header("Accept", "application/json, text/plain, */*")
                .header("Content-Type", "application/json")
                .body("{\"name\":\"刘正青\",\"nickname\":\"zk\",\"cards\":[{\"id\":1}],\"coupons\":[{\"id\":1}],\"myGroups\":[{\"id\":1}]}")
                .asString();

    }

}
