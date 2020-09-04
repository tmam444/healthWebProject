package com.healthchang.demo.controller;

import com.healthchang.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SearchController {

    @Autowired
    MemberService memberService;

    //ID Ajax 처리
    @GetMapping("/checkId")
    public boolean checkId(@Valid String id){
        return memberService.checkDuplicationId(id);
    }

}
