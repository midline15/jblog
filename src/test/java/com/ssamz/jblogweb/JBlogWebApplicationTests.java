package com.ssamz.jblogweb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class JBlogWebApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void passwordEncode() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "abc123@@";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("암호화 전 비밀번호: " + rawPassword);
        System.out.println("암호화 후 비밀번호: " + encodedPassword);
        System.out.println("비밀번호 비교: " + encoder.matches(rawPassword, encodedPassword));
    }

}
