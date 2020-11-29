package com.healthchang.demo.controller;

import com.healthchang.demo.domain.member.MemberTable;
import com.healthchang.demo.dto.exercise.ExerciseCaloriesCategoryAndTypeDto;
import com.healthchang.demo.dto.exercise.ExerciseCaloriesListDto;
import com.healthchang.demo.dto.exercise.ExerciseModify;
import com.healthchang.demo.dto.exercise.ExerciseRequest;
import com.healthchang.demo.service.ExerciseService;
import com.healthchang.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final MemberService memberService;

    @GetMapping("/exercise")
    public String exercise(Model model, Principal principal){
        List<ExerciseCaloriesCategoryAndTypeDto> categoryList = exerciseService.findGroupByCategory();
        if(principal != null) {
            MemberTable memberTable = memberService.findByEmailEndingWith(principal.getName());
            List<ExerciseCaloriesCategoryAndTypeDto> userList = exerciseService.findByMyExercise(memberTable);
            for (int i = 0; i < userList.size(); i++) {
                categoryList.add(categoryList.size(), userList.get(i));
            }
        }
        model.addAttribute("list", categoryList);
        return "exercise";
    }

    @PostMapping("/exercise_description")
    @ResponseBody
    public String exercise_Description(@RequestParam(value = "keyword", defaultValue = "") String keyword){
        String urlstr = "https://www.googleapis.com/customsearch/v1?key=AIzaSyCFEBLuMeI9kLwjOG22tzlVO60RXr-nldo&cx=6763bf449d6cfdf94&q=" + keyword;
        StringBuffer stringBuffer = CommonMethod.apiURLimport(urlstr, "GET");
        return stringBuffer.toString();
    }

    @PostMapping("/exercise_findType")
    @ResponseBody
    public String exercise_findType(@RequestParam(value = "keyword", defaultValue = "전체") String keyword){
        List<ExerciseCaloriesCategoryAndTypeDto> list = null;
        if(keyword.equals("전체"))
            list = exerciseService.findAllByGroupByType();
        else
            list = exerciseService.findGroupByTypeAndCategoryEquals(keyword);

        return CommonMethod.inputListOutputJson(list).toString();
    }

    @PostMapping("/exercise_findList")
    @ResponseBody
    public String exercise_findList(@RequestParam(value = "category", defaultValue = "전체") String category, @RequestParam(value = "type", defaultValue = "미정") String type, Principal principal) {
        //사용할 리스트 및 검색에 사용할 Member
        List<ExerciseCaloriesListDto> list = null;
        MemberTable memberTable = null;

        //로그인 되어 있다면, member 객체를 가져온다.
        if(principal != null)
            memberTable = memberService.findByEmailEndingWith(principal.getName());

        //카테고리 및 내가 추가한 운동 또는 다른 카테고리를 불러오는 로직
        if (category.equals("전체")) {
            if (type.equals("전체")) list = exerciseService.findAllListDtoByMemberIdIsNullOrMemberIdEquals(memberTable);
            else list = exerciseService.findByTypeAndMemberIdIsNull(type);

        }else if(category.equals("내가 추가한 운동")){
            if(type.equals("전체")) list = exerciseService.findAllByMemberIdEquals(memberTable);
            else list = exerciseService.findByCategoryAndTypeAndMemberIdEquals(category, type, memberTable);

        }else{
            if(type.equals("전체")) list = exerciseService.findByCategoryAndMemberIdIsNull(category);
            else list = exerciseService.findByCategoryAndTypeAndMemberIdIsNull(category, type);
        }

        //List를 Json 형식으로 변경하여 ajax 메소드로 돌려준다.
        return CommonMethod.inputListOutputJson(list).toString();
    }

    @PostMapping("/exerciseAdd")
    public String exerciseAdd(@Valid ExerciseRequest exercise, @RequestParam("weight") double weight, Principal principal){
        exercise.setCalorie((Math.round(exercise.getCalorie() / weight * 100)/100.0));
        if(principal != null){
            exercise.setMemberId(memberService.findByEmailEndingWith(principal.getName()));
            exerciseService.save(exercise);
        }
        return "redirect:/exercise";
    }


    @PostMapping("/exerciseModify")
    public String exerciseModify(@Valid ExerciseModify exerciseModify, Principal principal){
        exerciseModify.setCalorie((Math.round((exerciseModify.getCalorie() / 70) * 100)/100.0));
        if(principal != null){
            Long memberId = memberService.findByEmailEndingWith(principal.getName()).getId();
            Long exerciseMemberid = exerciseService.findById(exerciseModify.getId()).getMemberId().getId();
            if(memberId == exerciseMemberid){
                exerciseService.update(exerciseModify);
            }
        }
        return "redirect:/exercise";
    }

    @GetMapping("/exerciseDelete")
    public String exerciseDelete(@RequestParam("id") int id, Principal principal){
        if(principal != null){
            Long memberId = memberService.findByEmailEndingWith(principal.getName()).getId();
            Long exerciseMemberid = exerciseService.findById((long) id).getMemberId().getId();
            if(memberId == exerciseMemberid){
                exerciseService.deleteById((long) id);
            }
        }
        return "redirect:/exercise";
    }

}
