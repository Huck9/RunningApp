package com.example.runningapp.Dto;

import com.example.runningapp.Entity.Training;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingsDto {
    private List<Training> trainings;
}
