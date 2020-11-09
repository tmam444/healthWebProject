package com.healthchang.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public String loginForm2(Principal principal){
        if(principal != null){ // 로그인되어 있는 상태라면
            return "redirect:/";
        }
        return "login";
    }

}
