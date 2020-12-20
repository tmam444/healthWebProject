package com.healthchang.demo.controller;

import com.healthchang.demo.dto.healthSupplement.HealthSupplementDto;
import com.healthchang.demo.dto.healthSupplement.HealthSupplementTypeDto;
import com.healthchang.demo.service.HealthSupplementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HealthSupplementController {

    private final HealthSupplementService healthSupplementService;

    @GetMapping("/healthSupplement")
    public String supplementPage(Model model){
        List<HealthSupplementDto> list = healthSupplementService.getList();
        List<HealthSupplementTypeDto> typeList = healthSupplementService.getType();
        model.addAttribute("list", list);
        model.addAttribute("typeList", typeList);
        return "healthSupplement";
    }

}
