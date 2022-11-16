package com.zk.dirt;

import com.zk.dirt.core.DirtContext;
import com.zk.dirt.service.DirtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = DirtApplication.class)
public class DirtContextTest {

	@Autowired
	DirtService dirtService;

	@Autowired
	DirtContext dirtContext;


	@Test
	void name() {
		System.out.println(dirtService);
		for (String allEntityName : dirtContext.getAllEntityNames()) {
			System.out.println(allEntityName);
		}
	}
}
