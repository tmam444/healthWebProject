package com.healthchang.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
@Slf4j
public class LoginController {

    @RequestMapping(value = "/loginPage", method = {RequestMethod.POST, RequestMethod.GET})
    public String loginForm2(Principal principal){
        if(principal != null){ // 로그인되어 있는 상태라면
            return "redirect:/";
        }
        return "login";
    }

}
