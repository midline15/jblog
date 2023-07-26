package com.ssamz.jblogweb.controller;

import com.ssamz.jblogweb.DTO.ResponseDTO;
import com.ssamz.jblogweb.domain.User;
import com.ssamz.jblogweb.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final UserService userService;

    @PostMapping("/auth/login")
    public @ResponseBody ResponseDTO<?> login(@RequestBody User user, HttpSession session) {
        User findUser = userService.getUser(user.getUsername());

        if (findUser.getUsername() == null) {
            return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "아이디가 존재하지 않아요.");
        } else {
            if (user.getPassword().equals(findUser.getPassword())) {
                session.setAttribute("principal", findUser);
                return new ResponseDTO<>(HttpStatus.OK.value(), findUser.getUsername() + "님 로그인 성공하셨습니다.");
            } else {
                return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "비밀번호 오류!");
            }
        }
    }

    @GetMapping("/auth/login")
    public String login() {
        return "system/login";
    }

    @GetMapping("/auth/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
