package com.example.runningapp.Service;

import com.example.runningapp.Dto.CyclingRecordsDto;
import com.example.runningapp.Dto.RecordsDto;
import com.example.runningapp.Dto.SwimRecordsDto;
import com.example.runningapp.Dto.TrainingsDto;
import com.example.runningapp.Entity.Training;
import com.example.runningapp.Entity.TrainingStatistic;
import com.example.runningapp.Entity.User;
import com.example.runningapp.Repository.TrainingRepository;
import com.example.runningapp.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;

    public void saveTrainingsFromImport(TrainingsDto trainingsDto) {
        List<Training> trainings = trainingsDto.getTrainings().stream()
                .filter(Training::isImported)
                .collect(Collectors.toList());
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        trainings.forEach(t -> t.setUser(user));
        trainingRepository.saveAll(trainings);
    }

    public List<Training> getTrainings() {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return trainingRepository.findAllByUser(user);
    }

    public void saveTraingin(Training training) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        LocalTime time =  LocalTime.of(0,training.getAvgPace().getHour(),training.getAvgPace().getMinute());

        training.setAvgPace(time);
        training.setUser(user);
        trainingRepository.save(training);
    }

    public Training getTraining(Long id) {
        return trainingRepository.getById(id);
    }

    @Transactional
    public void editTraining(Training training) {
        Training trainingEntity = trainingRepository.getById(training.getId());
        trainingEntity.setWorkoutType(training.getWorkoutType());
        trainingEntity.setDistance(training.getDistance());
        trainingEntity.setStartDate(training.getStartDate());
        trainingEntity.setAvgPace(training.getAvgPace());
        trainingEntity.setAvgSpeed(training.getAvgSpeed());
        trainingEntity.setCalories(training.getCalories());
        trainingEntity.setMovingTime(training.getMovingTime());
        trainingEntity.setName(training.getName());
        trainingRepository.save(trainingEntity);
    }

    public void deleteTraining(Long index) {
        Training trainingEntity = trainingRepository.getById(index);
        trainingEntity.setUser(null);
        trainingRepository.delete(trainingEntity);
    }

    public TrainingStatistic getMonthStatistic(LocalDate date, String workoutType) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        TrainingStatistic trainingStatistic = getMonthStatistic(user.getId(), Long.valueOf(date.getYear()), Long.valueOf(date.getMonthValue()), workoutType);
        return trainingStatistic;
    }

    public TrainingStatistic getYearStatistic(Long year, String workoutType) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        TrainingStatistic trainingStatistic = getYearStatistic(user.getId(), year, workoutType);
        return trainingStatistic;
    }

    public TrainingStatistic getMonthStatistic(Long userId, Long year, Long month, String workoutType) {
        Object[] statistic = trainingRepository.getMonthStatistic(userId, year, month,workoutType);
        if (statistic != null) {
            Object[] stat = (Object[]) statistic[0];

            return TrainingStatistic.builder()
                    .trainingCount(Long.valueOf(String.valueOf(stat[0])))
                    .avgDistance(stat[1] == null ? 0F : Float.valueOf(String.valueOf(stat[1])))
                    .sumDistance(stat[2] == null ? 0F : Float.valueOf(String.valueOf(stat[2])))
                    .avgMovingTime(stat[3] == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(stat[3])))
                    .sumMovingTime(stat[4] == null ? String.valueOf(LocalTime.parse("00:00:00")) : String.valueOf(stat[4]))
                    .avgCalories(stat[5] == null ? 0F : Float.valueOf(String.valueOf(stat[5])))
                    .sumCalories(stat[6] == null ? 0F : Float.valueOf(String.valueOf(stat[6])))
                    .avgSpeed(stat[7] == null ? 0F : Float.valueOf(String.valueOf(stat[7])))
                    .avgPace(stat[8] == null ? LocalTime.parse("00:00:00.001") : LocalTime.parse(String.valueOf(stat[8])))
                    .build();
        } else {
            return new TrainingStatistic();
        }
    }

    public TrainingStatistic getYearStatistic(Long userId, Long year, String workoutType) {
        Object[] statistic = trainingRepository.getYearStatistic(userId, year, workoutType);
        Object[] stat = (Object[]) statistic[0];

        return TrainingStatistic.builder()
                .trainingCount(Long.valueOf(String.valueOf(stat[0])))
                .avgDistance(stat[1] == null ? 0F : Float.valueOf(String.valueOf(stat[1])))
                .sumDistance(stat[2] == null ? 0F : Float.valueOf(String.valueOf(stat[2])))
                .avgMovingTime(stat[3] == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(stat[3])))
                .sumMovingTime(stat[4] == null ? String.valueOf(LocalTime.parse("00:00:00")) : String.valueOf(stat[4]))
                .avgCalories(stat[5] == null ? 0F : Float.valueOf(String.valueOf(stat[5])))
                .sumCalories(stat[6] == null ? 0F : Float.valueOf(String.valueOf(stat[6])))
                .avgSpeed(stat[7] == null ? 0F : Float.valueOf(String.valueOf(stat[7])))
                .avgPace(stat[8] == null ? LocalTime.parse("00:00:00.001") : LocalTime.parse(String.valueOf(stat[8])))
                .build();
    }

    public TrainingStatistic getAllStatistic(Long userId) {
        Object[] statistic = trainingRepository.getAllStatistic(userId);
        if (statistic != null) {
            Object[] stat = (Object[]) statistic[0];

            return TrainingStatistic.builder()
                    .trainingCount(Long.valueOf(String.valueOf(stat[0])))
                    .avgDistance(stat[1] == null ? 0F : Float.valueOf(String.valueOf(stat[1])))
                    .sumDistance(stat[2] == null ? 0F : Float.valueOf(String.valueOf(stat[2])))
                    .avgMovingTime(stat[3] == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(stat[3])))
                    .sumMovingTime(stat[4] == null ? String.valueOf(LocalTime.parse("00:00:00")) : String.valueOf(stat[4]))
                    .avgCalories(stat[5] == null ? 0F : Float.valueOf(String.valueOf(stat[5])))
                    .sumCalories(stat[6] == null ? 0F : Float.valueOf(String.valueOf(stat[6])))
                    .avgSpeed(stat[7] == null ? 0F : Float.valueOf(String.valueOf(stat[7])))
                    .avgPace(stat[8] == null ? LocalTime.parse("00:00:00.001") : LocalTime.parse(String.valueOf(stat[8])))
                    .build();
        } else {
            return new TrainingStatistic();
        }
    }

    public TrainingStatistic analize(List<Training> trainings){
       List<Long> analize = trainings.stream()
               .filter(Training::isImported)
               .map(Training::getId)
               .collect(Collectors.toList());

        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        Object[] statistic = trainingRepository.getStatisticByTrainingId(user.getId(),analize);

        if (statistic != null) {
            Object[] stat = (Object[]) statistic[0];

            return TrainingStatistic.builder()
                    .trainingCount(Long.valueOf(String.valueOf(stat[0])))
                    .avgDistance(stat[1] == null ? 0F : Float.valueOf(String.valueOf(stat[1])))
                    .sumDistance(stat[2] == null ? 0F : Float.valueOf(String.valueOf(stat[2])))
                    .avgMovingTime(stat[3] == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(stat[3])))
                    .sumMovingTime(stat[4] == null ? String.valueOf(LocalTime.parse("00:00:00")) : String.valueOf(stat[4]))
                    .avgCalories(stat[5] == null ? 0F : Float.valueOf(String.valueOf(stat[5])))
                    .sumCalories(stat[6] == null ? 0F : Float.valueOf(String.valueOf(stat[6])))
                    .avgSpeed(stat[7] == null ? 0F : Float.valueOf(String.valueOf(stat[7])))
                    .avgPace(stat[8] == null ? LocalTime.parse("00:00:00.001") : LocalTime.parse(String.valueOf(stat[8])))
                    .build();
        } else {
            return new TrainingStatistic();
        }
    }

    public RecordsDto getRecords(){
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        RecordsDto runningRecordsDto = new RecordsDto();
        Object[] statistic = trainingRepository.getRunningRecords(user.getId());
        Object fiveKm = trainingRepository.get5Km(user.getId());
        Object tenKm = trainingRepository.get10Km(user.getId());
        Object halfmarathon = trainingRepository.get21Km(user.getId());
        Object marathon = trainingRepository.get42Km(user.getId());
        if (statistic != null) {
            Object[] stat = (Object[]) statistic[0];
            runningRecordsDto.setSumCalories( stat[0] == null ? 0F : Float.valueOf(String.valueOf(stat[0])));
            runningRecordsDto.setAvgSpeed(stat[1] == null ? 0F : Float.valueOf(String.valueOf(stat[1])));
            runningRecordsDto.setAvgPace(stat[2] == null ? LocalTime.parse("00:00:00.001") : LocalTime.parse(String.valueOf(stat[2])));
            runningRecordsDto.setSumDistance(stat[3] == null ? 0F : Float.valueOf(String.valueOf(stat[3])));
            runningRecordsDto.setAvgMovingTime(stat[4] == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(stat[4])));
            runningRecordsDto.setFivekm(fiveKm == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(fiveKm)));
            runningRecordsDto.setTenkm(tenKm == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(tenKm)));
            runningRecordsDto.setHalfmarathon(halfmarathon == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(halfmarathon)));
            runningRecordsDto.setMarathon(marathon == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(marathon)));

        }
        return runningRecordsDto;
    }

    public CyclingRecordsDto getCyclingRecords(){
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        CyclingRecordsDto cyclingRecordsDto = new CyclingRecordsDto();
        Object[] statistic = trainingRepository.getCyclingRecords(user.getId());
        Object ten = trainingRepository.get10KmC(user.getId());
        Object twenty = trainingRepository.get25Km(user.getId());
        Object fifty = trainingRepository.get50Km(user.getId());
        Object hundred = trainingRepository.get100Km(user.getId());
        if (statistic != null) {
            Object[] stat = (Object[]) statistic[0];
            cyclingRecordsDto.setSumCalories( stat[0] == null ? 0F : Float.valueOf(String.valueOf(stat[0])));
            cyclingRecordsDto.setAvgSpeed(stat[1] == null ? 0F : Float.valueOf(String.valueOf(stat[1])));
            cyclingRecordsDto.setAvgPace(stat[2] == null ? LocalTime.parse("00:00:00.001") : LocalTime.parse(String.valueOf(stat[2])));
            cyclingRecordsDto.setSumDistance(stat[3] == null ? 0F : Float.valueOf(String.valueOf(stat[3])));
            cyclingRecordsDto.setAvgMovingTime(stat[4] == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(stat[4])));
            cyclingRecordsDto.setTenkm(ten == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(ten)));
            cyclingRecordsDto.setTwentyFiveKm(twenty == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(twenty)));
            cyclingRecordsDto.setFiftyKm(fifty == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(fifty)));
            cyclingRecordsDto.setHundredKm(hundred == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(hundred)));

        }
        return cyclingRecordsDto;
    }

    public SwimRecordsDto getSwimRecords(){
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        SwimRecordsDto swimRecordsDto = new SwimRecordsDto();
        Object[] statistic = trainingRepository.getSwimRecords(user.getId());
        Object m50 = trainingRepository.get50m(user.getId());
        Object m100 = trainingRepository.get100m(user.getId());
        Object m200 = trainingRepository.get200m(user.getId());
        Object m400 = trainingRepository.get400m(user.getId());
        Object m800 = trainingRepository.get800m(user.getId());
        Object m1500 = trainingRepository.get1500m(user.getId());
        if (statistic != null) {
            Object[] stat = (Object[]) statistic[0];
            swimRecordsDto.setSumCalories( stat[0] == null ? 0F : Float.valueOf(String.valueOf(stat[0])));
            swimRecordsDto.setAvgSpeed(stat[1] == null ? 0F : Float.valueOf(String.valueOf(stat[1])));
            swimRecordsDto.setAvgPace(stat[2] == null ? LocalTime.parse("00:00:00.001") : LocalTime.parse(String.valueOf(stat[2])));
            swimRecordsDto.setSumDistance(stat[3] == null ? 0F : Float.valueOf(String.valueOf(stat[3])));
            swimRecordsDto.setAvgMovingTime(stat[4] == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(stat[4])));
            swimRecordsDto.setM50(m50 == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(m50)));
            swimRecordsDto.setM100(m100 == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(m100)));
            swimRecordsDto.setM200(m200 == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(m200)));
            swimRecordsDto.setM400(m400 == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(m400)));
            swimRecordsDto.setM800(m800 == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(m800)));
            swimRecordsDto.setM1500(m1500 == null ? LocalTime.parse("00:00:00") : LocalTime.parse(String.valueOf(m1500)));
        }
        return swimRecordsDto;
    }
}
