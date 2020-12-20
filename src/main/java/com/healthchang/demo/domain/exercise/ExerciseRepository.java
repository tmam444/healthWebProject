package com.healthchang.demo.domain.exercise;

import com.healthchang.demo.domain.member.MemberTable;
import com.healthchang.demo.dto.exercise.ExerciseCaloriesCategoryAndTypeDto;
import com.healthchang.demo.dto.exercise.ExerciseCaloriesListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<ExerciseCalories, Long> {

    @Query(value =
            "select '전체' as category, count(*) as count from exercise_calories where category != '내가 추가한 운동'" +
            " union " +
            "select category as category, count(category) as count from exercise_calories where category != '내가 추가한 운동' group by category",
            nativeQuery = true)
    List<ExerciseCaloriesCategoryAndTypeDto> findGroupByCategory();


    // keyword 카테고리의 전체 갯수 / 카테고리의 각 타입별 그룹핑하여 갯수 확인
    @Query(value = "select category as category , '전체' as type, count(*) as count from exercise_calories where category = :category" +
            " union " +
            "SELECT category as category, type as 'type', count(*) as count FROM exercise_calories where category = :category and type != '' group by type", nativeQuery = true)
    List<ExerciseCaloriesCategoryAndTypeDto> findGroupByTypeAndCategoryEquals(@Param("category") String category);

    // 내가 추가한 운동 타입 찾기
    @Query(value = "select category as category , '전체' as type, count(*) as count from exercise_calories where category = :category and member_id = :memberId" +
            " union " +
            "SELECT category as category, type as 'type', count(*) as count FROM exercise_calories where category = :category and member_id = :memberId group by type", nativeQuery = true)
    List<ExerciseCaloriesCategoryAndTypeDto> findGroupByTypeAndCategoryEqualsAndMemberIdEquals(@Param("category") String category, @Param("memberId") String memberId);


    @Query(value = "select '전체' as type, count(*) as count from exercise_calories where category != '내가 추가한 운동'" +
            " union " +
            "SELECT if(type = '', '미정', type) as type, count(type) as count FROM exercise_calories where category != '내가 추가한 운동' group by type order by count desc", nativeQuery = true)
    List<ExerciseCaloriesCategoryAndTypeDto> findAllByGroupByType();

    @Query(value = "select category as category, count(category) as count from exercise_calories where member_id = :memberId group by category", nativeQuery = true)
    List<ExerciseCaloriesCategoryAndTypeDto> findByMyExercise(@Param("memberId") MemberTable memberId);


    //회원 리스트
    List<ExerciseCaloriesListDto> findAllByMemberIdEquals(MemberTable memberTable);
    List<ExerciseCaloriesListDto> findByCategoryAndTypeAndMemberIdEquals(String category, String type, MemberTable memberId);

    //일반 리스트
    List<ExerciseCaloriesListDto> findAllListDtoByMemberIdIsNullOrMemberIdEquals(MemberTable memberId);
    List<ExerciseCaloriesListDto> findByTypeAndMemberIdIsNull(String type);
    List<ExerciseCaloriesListDto> findByCategoryAndMemberIdIsNull(String category);
    List<ExerciseCaloriesListDto> findByCategoryAndTypeAndMemberIdIsNull(String category, String type);


}
