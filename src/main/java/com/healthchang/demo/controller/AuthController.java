package com.healthchang.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {

    //Link
    @GetMapping("/goNaverLogin")
    public String goNaverLogin() {
        return "APIExamNaverLogin";
    }

    @GetMapping("/auth/naver/callback")
    public String naverLoginCallBack() {
        return "callback";
    }

    //Callback
    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String kakaoLoginCallBack(){
        return "카카오 인증 완료";
    }




}
