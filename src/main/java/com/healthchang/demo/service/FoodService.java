package com.healthchang.demo.service;

import com.healthchang.demo.domain.food.FoodCalorie;
import com.healthchang.demo.domain.food.FoodCalorieRepository;
import com.healthchang.demo.dto.food.FoodSearchListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodCalorieRepository foodCalorieRepository;

    public Page<FoodCalorie> findAll(PageRequest of) {
        Page<FoodCalorie> list = foodCalorieRepository.findAll(of);
        return list;
    }

    public Page<FoodSearchListDto> findFood(String search, PageRequest of) {
        Page<FoodSearchListDto> list = foodCalorieRepository.findByNameContaining(search, of);
        return list;
    }
}
