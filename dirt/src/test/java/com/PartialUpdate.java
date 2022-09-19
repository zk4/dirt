//package com;
//
//import com.zk.DirtApplication;
//import com.zk.dao.UserDao;
//import com.zk.entity.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Optional;
//
//@SpringBootTest(classes = {DirtApplication.class})
//public class PartialUpdate {
//
//	@Autowired
//	UserDao userRepo;
//
//	@Test
//	void partialTest() {
//		Optional<User> byId = userRepo.findById(1L);
//		byId.ifPresent(user -> {
//			user.setGender(null);
//			userRepo.save(user);
//		});
//
//
//	}
//}
