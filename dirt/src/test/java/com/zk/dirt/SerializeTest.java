package com.zk.dirt;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("mysql")
public class SerializeTest {

    //@Autowired
    //ObjectMapper objectMapper;
    //@Test
    //void name() throws JsonProcessingException {
    //    String json = "{1:{\"text\":1,\"status\":1,\"color\":\"\"},2:{\"text\":2,\"status\":2,\"color\":\"\"}}";
    //    Map map = objectMapper.readValue(json, Map.class);
    //    System.out.println(map);
    //}
}
