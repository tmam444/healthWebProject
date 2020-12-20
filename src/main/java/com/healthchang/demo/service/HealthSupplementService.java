package com.healthchang.demo.service;

import com.healthchang.demo.domain.healthSupplement.HealthSupplementRepository;
import com.healthchang.demo.dto.healthSupplement.HealthSupplementDto;
import com.healthchang.demo.dto.healthSupplement.HealthSupplementTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthSupplementService {

    private final HealthSupplementRepository healthSupplementRepository;


    public List<HealthSupplementDto> getList() {
        return healthSupplementRepository.findAllBy();
    }

    public List<HealthSupplementTypeDto> getType(){
        return healthSupplementRepository.findGroupByType();
    }

}
