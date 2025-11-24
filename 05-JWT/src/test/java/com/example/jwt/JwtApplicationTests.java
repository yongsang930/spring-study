package com.example.jwt;

import com.example.jwt.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

@SpringBootTest
class JwtApplicationTests {

    @Autowired
    private JwtService jwtService;

	@Test
	void contextLoads() {
	}

    @Test
    void tokenCreate() {

        var claims = new HashMap<String, Object>();
        claims.put("user_id", 923);

        var expireAt = LocalDateTime.now().plusSeconds(30);

        var jwtToken = jwtService.create(claims, expireAt);

        System.out.println(jwtToken);

    }

    @Test
    void tokenValidation() {

        var token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo5MjMsImV4cCI6MTc2Mzk4NTk2MX0.QU6KCfXnTyw9Xqh0p-Vq8ijIh9PMRFJLMNA7iB6aNf4";

        jwtService.validation(token);
    }
}
