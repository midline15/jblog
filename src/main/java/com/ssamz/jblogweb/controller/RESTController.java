package com.ssamz.jblogweb.controller;

import com.ssamz.jblogweb.domain.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class RESTController {

    @GetMapping("/jblog")
    public User httpGet() {
        User findUser = User.builder()
                .id(1L)
                .username("gurum")
                .password("222")
                .email("gurum@gmail.com")
                .build();
        return findUser;
    }

    @PostMapping("/jblog")
    public String httpPost(@RequestBody User user) {
        return "POST 요청 처리 입력값 : " + user.toString();
    }

    @PutMapping("/jblog")
    public String httpPut(@RequestBody User user) {
        return "PUT 요청 처리 입력값 : " + user.toString();
    }

    @DeleteMapping("/jblog")
    public String httpDelete(@RequestParam Long id) {
        return "DELETE 요청 처리 입력값 : " + id;
    }
}
