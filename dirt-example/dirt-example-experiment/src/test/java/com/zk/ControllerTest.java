package com.zk;

import com.google.common.collect.ImmutableMap;
import com.zk.dirt.rest.DirtController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes = ExperimentApplication.class)
public class ControllerTest {

    @Autowired
    DirtController dirtController;

    @Test
    void controllerTest() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        HashMap hashMap = new HashMap();
        hashMap.put("name", "刘正青");
        hashMap.put("nickname", "zk");
        //hashMap.put("cards", Arrays.asList(ImmutableMap.of("id",1)));
        hashMap.put("coupons", Arrays.asList(ImmutableMap.of("id",1)));
        //hashMap.put("myGroups", Arrays.asList(ImmutableMap.of("id",1)));

        dirtController.create("com.zk.experiment.Member",hashMap);
    }
}
