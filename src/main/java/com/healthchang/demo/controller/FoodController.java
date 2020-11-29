package com.healthchang.demo.controller;

import com.healthchang.demo.domain.food.FoodCalorieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class FoodController {

    private final FoodCalorieRepository foodCalorieRepository;

    @GetMapping("/food")
    public String foodPage(){
        return "food";
    }

    @PostMapping("/foodSearch")
    public String foodSearch(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "search", defaultValue = "") String search) {
        System.out.println(foodCalorieRepository.findAll());
        System.out.println(page + ", " + search);
        return "food";
    }

}
