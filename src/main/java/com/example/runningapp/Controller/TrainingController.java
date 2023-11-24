package com.example.runningapp.Controller;

import com.example.runningapp.Dto.MonthDto;
import com.example.runningapp.Dto.TrainingTypeDto;
import com.example.runningapp.Dto.TrainingsDto;
import com.example.runningapp.Dto.YearDto;
import com.example.runningapp.Entity.Training;
import com.example.runningapp.Service.StravaService;
import com.example.runningapp.Service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/training")
@RequiredArgsConstructor
public class TrainingController {

    private final StravaService stravaService;
    private final TrainingService trainingService;

    @GetMapping("/import")
    public String importTraining(Model model) {
        TrainingsDto trainingsDto = TrainingsDto.builder()
                .trainings(stravaService.getTrainings(SecurityContextHolder.getContext().getAuthentication().getName()))
                .build();
        model.addAttribute("trainingDto", trainingsDto);
        return "trainingsImport";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute TrainingsDto trainingsDto) {
        trainingService.saveTrainingsFromImport(trainingsDto);
        return "redirect:/training/all";
    }

    @GetMapping("/all")
    public String trainings(Model model) {
        TrainingsDto trainingsDto = TrainingsDto.builder()
                .trainings(trainingService.getTrainings())
                .build();
        model.addAttribute("trainingDto", trainingsDto);
        return "trainings";
    }

    @PostMapping("/saveNew")
    public String save(Training training) {
        trainingService.saveTraingin(training);
        return "redirect:/training/all";
    }

    @GetMapping("/add")
    public String add(Model model) {
        Training training = new Training();
        model.addAttribute("training", training);
        return "addTraining";
    }

    @GetMapping("/edit/{index}")
    public String editRecord(@PathVariable Long index, Model model) {
        Training training = trainingService.getTraining(index);
        model.addAttribute("training", training);
        return "editTraining";
    }

    @PostMapping("/saveEdit")
    public String saveEdit(Training training) {
        trainingService.editTraining(training);
        return "redirect:/training/all";
    }

    @GetMapping("/delete/{index}")
    private String deleteTraining(@PathVariable Long index) {
        trainingService.deleteTraining(index);
        return "redirect:/training/all";
    }

    @PostMapping("/statistic/month")
    private String postMonthStatistic(@ModelAttribute MonthDto monthDto, Model model) {
        String date = monthDto.getDate() + "-01";
        model.addAttribute("monthDto", monthDto);
        model.addAttribute("statistic", trainingService.getMonthStatistic(LocalDate.parse(date), monthDto.getTrainingType()));
        return "monthStatistic";
    }

    @GetMapping("/statistic/month")
    private String getMonthStatistic(Model model) {
        MonthDto monthDto = new MonthDto();
        StringBuilder sb = new StringBuilder(LocalDate.now().toString());
        monthDto.setTrainingType("Run");
        monthDto.setDate(sb.substring(0, 7));
        System.out.println(sb.substring(0, 7));
        model.addAttribute("monthDto", monthDto);
        model.addAttribute("statistic", trainingService.getMonthStatistic(LocalDate.now(), monthDto.getTrainingType()));
        return "monthStatistic";
    }

    @GetMapping("/statistic/year")
    private String getYearStatistic(Model model) {
        YearDto yearDto = new YearDto();
        yearDto.setTrainingType("Run");
        yearDto.setYear(2021L);
        model.addAttribute("yearDto", yearDto);
        model.addAttribute("statistic", trainingService.getYearStatistic(yearDto.getYear(), "Run"));
        return "yearStatistic";
    }

    @PostMapping("/statistic/year")
    private String postYearStatistic(@ModelAttribute YearDto yearDto, Model model) {
        model.addAttribute("yearDto", yearDto);
        model.addAttribute("statistic", trainingService.getYearStatistic(yearDto.getYear(), yearDto.getTrainingType()));
        return "yearStatistic";
    }

    @PostMapping("/analize")
    private String getAnalize(@ModelAttribute TrainingsDto trainingsDto, Model model) {
        model.addAttribute("statistic", trainingService.analize(trainingsDto.getTrainings()));
        return "statistic";
    }

    @GetMapping("/records")
    private String getRecords(Model model){
        TrainingTypeDto trainingTypeDto = new TrainingTypeDto();
        trainingTypeDto.setTrainingType("Run");
        model.addAttribute("records", trainingService.getRecords());
        model.addAttribute("cycling",trainingService.getCyclingRecords() );
        model.addAttribute("swim",trainingService.getSwimRecords() );
        model.addAttribute("trainingType", trainingTypeDto);
        return "records";
    }

    @PostMapping("/records")
    private String postRecords(Model model, @ModelAttribute TrainingTypeDto trainingType){
        model.addAttribute("records", trainingService.getRecords());
        model.addAttribute("cycling",trainingService.getCyclingRecords() );
        model.addAttribute("swim",trainingService.getSwimRecords() );
        model.addAttribute("trainingType", trainingType);
        System.out.println(trainingType);
        return "records";
    }
}
