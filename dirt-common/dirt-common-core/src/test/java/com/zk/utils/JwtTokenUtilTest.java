package com.zk.utils;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;

class JwtTokenUtilTest {


    private JwtTokenUtils jwtTokenUtil;

    @BeforeEach
    void setUp() {
        //jwtTokenUtil = new JwtTokenUtil();
        //String s = Base64.getEncoder().encodeToString("hello".getBytes());
        //jwtTokenUtil.setSecret(s);
    }

    @Test
    void getUsernameFromToken() {
    }

    @Test
    void getExpirationDateFromToken() {
    }

    @Test
    void getClaimFromToken() {
    }

    @Test
    void generateToken() {


        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("name", "zk");
        stringObjectHashMap.put("role", "admin");
        stringObjectHashMap.put("permission", "resource:action");
        String token = jwtTokenUtil.doGenerateToken(stringObjectHashMap, "me");
        System.out.println(token);

        Claims claimFromToken = jwtTokenUtil.getClaimFromToken(token, claims -> {
            return claims;
        });
        System.out.println(claimFromToken);


    }

    @Test
    void createPassword() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123");
        System.out.println(encode);
    }
}