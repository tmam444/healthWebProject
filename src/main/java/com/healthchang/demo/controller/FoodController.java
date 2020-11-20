package com.healthchang.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FoodController {

    @GetMapping("/food")
    public String foodPage(){
        return "food";
    }

    @ResponseBody
    @GetMapping("/foodSearch")
    public String foodSearch(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "find", defaultValue = "") String find) {

        return "hello";
    }

}
