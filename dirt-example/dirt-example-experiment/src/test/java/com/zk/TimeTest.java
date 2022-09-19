package com.zk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TimeTest {

    @Autowired
    ObjectMapper objectMapper;


    @Autowired
    TestRestTemplate restTemplate;
    String timeStr ="{" +
            "\"localDateTime\":\"2022-08-30 11:10:20\"," +
            "\"localDate\":\"2022-08-30\"," +
            "\"date\":\"2022-08-30 11:10:20\"," +
            "\"localTime\":\"11:10:20\"" +
            "}";
    @Test
    void timeConverterTest() throws JsonProcessingException {

        DataHolder dataHolder = objectMapper.readValue(timeStr, DataHolder.class);
        System.out.println(dataHolder);

    }

    @Test
    @DisplayName("使用 @autowired ObjectMapper")
    public void datetest() throws JsonProcessingException {
         String url = "/datetest";
        DataHolder dataHolder = new DataHolder();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String string = objectMapper.writeValueAsString(dataHolder);
        System.out.println(string);

        HttpEntity<String> request = new HttpEntity<String>(timeStr, headers);

        String response = restTemplate.postForObject(url,request,String.class);
        System.out.println(response);
        //assertThat(response).isEqualTo("{\"brand\":\"" + brand + "\",\"date\":\"" + formattedDate + "\"}");
    }
    @Test
    @DisplayName("使用 new ObjectMapper")
    public void datetest2() throws JsonProcessingException {
        String url = "/datetest2";
        DataHolder dataHolder = new DataHolder();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        String string = mapper.writeValueAsString(dataHolder);
        System.out.println(string);

        HttpEntity<String> request = new HttpEntity<String>(timeStr, headers);

        String response = restTemplate.postForObject(url,request,String.class);

        System.out.println(response);
        //assertThat(response).isEqualTo("{\"brand\":\"" + brand + "\",\"date\":\"" + formattedDate + "\"}");
    }
}
