package com.healthchang.demo.controller;

import com.healthchang.demo.dto.food.FoodSearchListDto;
import com.healthchang.demo.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/food")
    public String foodPage(){
        return "food";
    }

    @GetMapping("/foodSearch")
    public String foodSearch(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "search", defaultValue = "") String search, Model model) {
        Page<FoodSearchListDto> list = foodService.findFood(search, PageRequest.of(page, 10));
        model.addAttribute("list", list);
        model.addAttribute("CurPage", page);
        model.addAttribute("search", search);
        return "food";
    }

}
