package com.healthchang.demo.controller;

import com.healthchang.demo.domain.MemberTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class Example {

    private final HttpSession httpSession;

    @GetMapping("/")
    String home() {
//        System.out.println(principal);
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//        if(user != null){
//            model.addAttribute("userName", user.getName());
//        }
        return "main";
    }

    @GetMapping("/join")
    public String join(Model model){
        model.addAttribute("member", new MemberTable());
        return "register";
    }

    @GetMapping("/main")
    String main(){
        return "main";
    }

}
