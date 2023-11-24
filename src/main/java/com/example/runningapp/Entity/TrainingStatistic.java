package com.example.runningapp.Entity;

import lombok.*;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TrainingStatistic {
    Long trainingCount;
    Float avgDistance;
    Float sumDistance;
    LocalTime avgMovingTime;
    String sumMovingTime;
    Float avgCalories;
    Float sumCalories;
    Float avgSpeed;
    LocalTime avgPace;
}
