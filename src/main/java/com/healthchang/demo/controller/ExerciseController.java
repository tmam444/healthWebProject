package com.healthchang.demo.controller;

import com.healthchang.demo.domain.ExerciseCalories;
import com.healthchang.demo.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseRepository exerciseRepository;

    @GetMapping("/exercise")
    public String exerciseTemp(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "view", defaultValue = "abc") String view, Model model){
        long count = exerciseRepository.count();
        page = page <= 0 ? 1 : page;
        List<ExerciseCalories> list;
        if(view.equals("abc")){
            list = exerciseRepository.findAllByOrderByName();
        }else if (view.equals("calorieAsc")){
             list = exerciseRepository.findAllByOrderByCalorie();
        }else{
             list = exerciseRepository.findAllByOrderByCalorieDesc();
        }
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        model.addAttribute("count", count);
        model.addAttribute("view", view);
        return "exercise_temp";
    }

    @PostMapping("/exercise_description")
    @ResponseBody
    public String exercise_Description(@RequestParam(value = "keyword", defaultValue = "") String keyword){
        String urlstr = "https://www.googleapis.com/customsearch/v1?key=AIzaSyCFEBLuMeI9kLwjOG22tzlVO60RXr-nldo&cx=6763bf449d6cfdf94&q=" + keyword;
        StringBuffer stringBuffer = CommonMethod.apiURLimport(urlstr, "GET");
        return stringBuffer.toString();
    }

    @PostMapping("/exerciseAdd")
    public String exerciseAdd(){
        System.out.println("test");
        return "main";
    }

}
