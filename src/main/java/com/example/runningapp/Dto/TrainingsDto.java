package com.example.runningapp.Dto;

import com.example.runningapp.Entity.Training;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class TrainingsDto {
    private List<Training> trainings;
}
