package com.zk.dirt;

import com.zk.dirt.service.DirtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {

	@Autowired
	DirtService dirtService;

	public Object getByID(String id ){
		return id+" called";
	}
}
