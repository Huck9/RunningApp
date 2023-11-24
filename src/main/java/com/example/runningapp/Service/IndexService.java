package com.example.runningapp.Service;

import com.example.runningapp.Dto.IndexDto;
import com.example.runningapp.Entity.TrainingStatistic;
import com.example.runningapp.Entity.User;
import com.example.runningapp.Repository.TrainingRepository;
import com.example.runningapp.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class IndexService {

    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingService trainingService;

    public IndexDto getIndexData() {
        IndexDto indexDto = new IndexDto();

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

            indexDto.setTrainingCount(trainingRepository.countAllByUser(user));
            return indexDto;
        }
        return indexDto;
    }


    public TrainingStatistic getAllStatistic() {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user != null) {
            TrainingStatistic trainingStatistic = trainingService.getAllStatistic(user.getId());
            return Objects.requireNonNullElseGet(trainingStatistic, TrainingStatistic::new);
        } else {
            return new TrainingStatistic();
        }

    }
}
