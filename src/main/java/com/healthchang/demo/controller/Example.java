package com.healthchang.demo.controller;

import com.healthchang.demo.domain.MemberTable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Example {

    @GetMapping("/")
    String home() {
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
