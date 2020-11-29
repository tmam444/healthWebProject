package com.healthchang.demo.service;

import com.healthchang.demo.domain.exercise.ExerciseCalories;
import com.healthchang.demo.domain.exercise.ExerciseRepository;
import com.healthchang.demo.domain.member.MemberTable;
import com.healthchang.demo.dto.exercise.ExerciseCaloriesCategoryAndTypeDto;
import com.healthchang.demo.dto.exercise.ExerciseCaloriesListDto;
import com.healthchang.demo.dto.exercise.ExerciseModify;
import com.healthchang.demo.dto.exercise.ExerciseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Transactional
    public void save(ExerciseRequest exercise) {
        exerciseRepository.save(ExerciseCalories.builder()
                                    .category("내가 추가한 운동")
                                    .type(exercise.getType())
                                    .name(exercise.getName())
                                    .effect(exercise.getEffect())
                                    .calorie(exercise.getCalorie())
                                    .memberId(exercise.getMemberId())
                                .build());
    }

    @Transactional
    public void update(ExerciseModify exerciseModify) {
        exerciseRepository.findById(exerciseModify.getId()).get()
                .update(exerciseModify.getType(), exerciseModify.getName(), exerciseModify.getEffect(), exerciseModify.getCalorie());
    }

    @Transactional
    public void deleteById(long id) {
        exerciseRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ExerciseCalories findById(long id) {
        return exerciseRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<ExerciseCaloriesCategoryAndTypeDto> findGroupByCategory() {
        return exerciseRepository.findGroupByCategory();
    }

    @Transactional(readOnly = true)
    public List<ExerciseCaloriesCategoryAndTypeDto> findByMyExercise(MemberTable memberTable) {
        return exerciseRepository.findByMyExercise(memberTable);
    }

    @Transactional(readOnly = true)
    public List<ExerciseCaloriesCategoryAndTypeDto> findAllByGroupByType() {
        return exerciseRepository.findAllByGroupByType();
    }

    @Transactional(readOnly = true)
    public List<ExerciseCaloriesCategoryAndTypeDto> findGroupByTypeAndCategoryEquals(String keyword) {
        return exerciseRepository.findGroupByTypeAndCategoryEquals(keyword);
    }

    @Transactional(readOnly = true)
    public List<ExerciseCaloriesListDto> findAllListDtoByMemberIdIsNullOrMemberIdEquals(MemberTable memberTable) {
        return exerciseRepository.findAllListDtoByMemberIdIsNullOrMemberIdEquals(memberTable);
    }

    @Transactional(readOnly = true)
    public List<ExerciseCaloriesListDto> findByTypeAndMemberIdIsNull(String type) {
        return exerciseRepository.findByTypeAndMemberIdIsNull(type);
    }

    @Transactional(readOnly = true)
    public List<ExerciseCaloriesListDto> findAllByMemberIdEquals(MemberTable memberTable) {
        return exerciseRepository.findAllByMemberIdEquals(memberTable);
    }

    @Transactional(readOnly = true)
    public List<ExerciseCaloriesListDto> findByCategoryAndTypeAndMemberIdEquals(String category, String type, MemberTable memberTable) {
        return exerciseRepository.findByCategoryAndTypeAndMemberIdEquals(category, type, memberTable);
    }

    @Transactional(readOnly = true)
    public List<ExerciseCaloriesListDto> findByCategoryAndMemberIdIsNull(String category) {
        return exerciseRepository.findByCategoryAndMemberIdIsNull(category);
    }

    @Transactional(readOnly = true)
    public List<ExerciseCaloriesListDto> findByCategoryAndTypeAndMemberIdIsNull(String category, String type) {
        return exerciseRepository.findByCategoryAndTypeAndMemberIdIsNull(category, type);
    }

}
