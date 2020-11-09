package com.healthchang.demo.controller;

import com.healthchang.demo.domain.MemberTable;
import com.healthchang.demo.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/sendJoin")
    public String sendJoin(@Valid MemberTable member, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("member", member);
            return "register";
        }
        // ID Check Method
        if(memberService.save(member) == null){
            model.addAttribute("member", member);
            model.addAttribute("msg", "가입되어 있는 이메일입니다.");
            return "register";
        }else{
            return "redirect:/";
        }
    }
}
