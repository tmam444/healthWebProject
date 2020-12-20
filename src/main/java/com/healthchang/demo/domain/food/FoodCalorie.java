package com.healthchang.demo.domain.food;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class FoodCalorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column private String type;

    @Column private String name;

    @Column private String category;

    @Column private String unit;

    @Column private int ones;

    @Column private double calorie;

    @Column private double protein;

    @Column private double fat;

    @Column private double carbohydrate;

    @Column private double sugar;

    @Column private double dietaryFiber;

    @Column private double calcium;

    @Column private double magnesium;

    @Column private double kalium;

    @Column private double natrium;

    @Column private double cholesterol;

    @Column private double vitaminB1;

    @Column private double vitaminB2;

    @Column private double vitaminC;

    @Builder
    public FoodCalorie (String type, String name, String category, String unit, int ones, double calorie, double protein, double fat, double carbohydrate, double sugar, double dietary_fiber, double calcium, double magnesium, double kalium, double natrium, double vitaminB1, double vitaminB2, double vitaminC, double cholesterol){
        this.type = type;
        this.name = name;
        this.category = category;
        this.unit = unit;
        this.ones = ones;
        this.calorie = calorie;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.sugar = sugar;
        this.dietaryFiber = dietary_fiber;
        this.calcium = calcium;
        this.magnesium = magnesium;
        this.kalium = kalium;
        this.natrium = natrium;
        this.cholesterol = cholesterol;
        this.vitaminB1 = vitaminB1;
        this.vitaminB2 = vitaminB2;
        this.vitaminC = vitaminC;
    }



}

