package com.healthchang.demo.service;

import com.healthchang.demo.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;

    public String myPageGetName(String id){
        return memberRepository.findByEmailEndingWith(id).getName();
    }



}
