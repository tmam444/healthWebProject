package com.healthchang.demo.controller;

import com.healthchang.demo.domain.MemberTable;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Example {

    @GetMapping("/")
    String home(String name, Model model) {
        model.addAttribute("name", name);
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
