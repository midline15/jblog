package com.ssamz.jblogweb.service;

import com.google.gson.Gson;
import com.ssamz.jblogweb.domain.OAuthType;
import com.ssamz.jblogweb.domain.RoleType;
import com.ssamz.jblogweb.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class KakaoLoginService {

    @Value("${kakao.default.password}")
    private String kakaoPassword;

    public String getAccessToken(String code) {

        HttpHeaders header = new HttpHeaders();
        header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "a65165d3516d80b29d8c6ea61bd26e67");
        body.add("redirect_uri", "http://localhost:8080/oauth/kakao");
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, header);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        String jsonData = responseEntity.getBody();

        Gson gsonObj = new Gson();
        Map<?, ?> data = gsonObj.fromJson(jsonData, Map.class);

        return (String) data.get("access_token");
    }

    public User getUserInfo(String accessToken) {

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + accessToken);
        header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(header);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        String userInfo = responseEntity.getBody();

        Gson gsonObj = new Gson();
        Map<?, ?> data = gsonObj.fromJson(userInfo, Map.class);

        Double id = (Double) (data.get("id"));
        String nickname = (String) ((Map<?, ?>) (data.get("properties"))).get("nickname");
        String email = (String) ((Map<?, ?>) (data.get("kakao_account"))).get("email");

        User user = new User();
        user.setUsername(email);
        user.setPassword(kakaoPassword);
        user.setEmail(email);
        user.setRole(RoleType.USER);
        user.setOauth(OAuthType.KAKAO);

        return user;
    }
}
