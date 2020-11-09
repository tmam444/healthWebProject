package com.healthchang.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthchang.demo.config.auth.CustomOAuth2UserService;
import com.healthchang.demo.config.auth.dto.OAuthAttributes;
import com.healthchang.demo.config.auth.dto.SessionUser;
import com.healthchang.demo.config.auth.temp.KakaoProfile;
import com.healthchang.demo.config.auth.temp.OAuthToken;
import com.healthchang.demo.domain.MemberTable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final HttpSession httpSession;


    public AuthController(CustomOAuth2UserService customOAuth2UserService, HttpSession httpSession) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.httpSession = httpSession;
    }


    //Callback
    @GetMapping("/oauth2/code/kakao")
    public String kakaoLoginCallBack(String code){

        //POST방식으로 key=value 데이터를 요청 (카카오쪽으로)
        RestTemplate rt = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("grant_type", "authorization_code");
        param.add("client_id", "2c1e6cc30a132b4647e201a908fa932c");
        param.add("redirect_uri", "http://127.0.0.1:8080/oauth2/code/kakao");
        param.add("client_secret", "NL7SgK8FEv16yGPoXXBct0myw7yiYxSH");
        param.add("code", code);

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(param, headers);

        //Http 요청하기 - Post 방식으로 - 그리고response 변수의 응답 받음.
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        //Gson, Json Simple, ObjectMapper
        ObjectMapper obMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = obMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //POST방식으로 key=value 데이터를 요청 (카카오쪽으로)
        RestTemplate rt2 = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        //Http 요청하기 - Post 방식으로 - 그리고response 변수의 응답 받음.
        ResponseEntity<String> response2 = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        //Gson, Json Simple, ObjectMapper
        ObjectMapper obMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        System.out.println(response2.getBody());

        try {
            kakaoProfile = obMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        OAuthAttributes attributes =
                OAuthAttributes
                        .builder()
                        .email(kakaoProfile.getKakao_account().getEmail())
                        .name(kakaoProfile.properties.getNickname())
                        .picture(kakaoProfile.properties.getProfile_image())
                        .build();

        MemberTable user = customOAuth2UserService.saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return "redirect:/";
    }
}
