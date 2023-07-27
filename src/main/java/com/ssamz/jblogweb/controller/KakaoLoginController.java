package com.ssamz.jblogweb.controller;

import com.ssamz.jblogweb.domain.User;
import com.ssamz.jblogweb.security.UserDetailsImpl;
import com.ssamz.jblogweb.security.UserDetailsServiceImpl;
import com.ssamz.jblogweb.service.KakaoLoginService;
import com.ssamz.jblogweb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;

    @Value("${kakao.default.password}")
    private String kakaoPassword;

    @GetMapping("/oauth/kakao")
    public String kakaoCallback(String code) {
        String accessToken = kakaoLoginService.getAccessToken(code);

        User kakaoUser = kakaoLoginService.getUserInfo(accessToken);

        User findUser = userService.getUser(kakaoUser.getUsername());
        if (findUser.getUsername() == null) {
            userService.insertUser(kakaoUser);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), kakaoPassword);

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }
}
