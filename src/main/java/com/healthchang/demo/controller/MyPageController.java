package com.healthchang.demo.controller;

import com.healthchang.demo.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage")
    public String mypage(Principal principal, Model model){
        String name = myPageService.myPageGetName(principal.getName());
        model.addAttribute("name", name);
        return "mypage";
    }

}
