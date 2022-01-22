package com.example.runningapp.Controller;

import com.example.runningapp.Dto.TrainingsDto;
import com.example.runningapp.Entity.Training;
import com.example.runningapp.Service.StravaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Controller
@RequestMapping("/training")
@RequiredArgsConstructor
public class TrainingController {

    private final StravaService stravaService;

    @GetMapping("/import")
    public String importTraining(Model model) {
        TrainingsDto trainingsDto = TrainingsDto.builder()
                .trainings(stravaService.getTrainings(SecurityContextHolder.getContext().getAuthentication().getName()))
                .build();
        model.addAttribute("trainingDto", trainingsDto);
        return "trainings";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute TrainingsDto trainingsDto, Model model){
        System.out.println(trainingsDto.getTrainings());

        return "redirect:/index";
    }
}
