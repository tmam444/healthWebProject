package com.healthchang.demo.config.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class HealthInformation {

    private int list_total_count;
    private List<Object> healthInfos;

    @Builder
    public HealthInformation(int list_total_count, List<Object> healthInfos){
        this.list_total_count = list_total_count;
        this.healthInfos = healthInfos;
    }

}
