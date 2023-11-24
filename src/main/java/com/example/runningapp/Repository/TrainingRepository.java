package com.example.runningapp.Repository;

import com.example.runningapp.Entity.Training;
import com.example.runningapp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findAllByUser(User user);

    Long countAllByUser(User user);

    @Query(value = "select count(*) as traingingCount, " +
            "round(avg(distance),2) as avgDistance, " +
            "round(sum(distance),2) as sumDistance, " +
            "SEC_TO_TIME(AVG(TIME_TO_SEC(moving_time))) as avgMovingTime, " +
            "CAST(SEC_TO_TIME(sum(TIME_TO_SEC(moving_time))) as CHAR) as sumMovingTime, " +
            "round(avg(calories),2) as avgCalories, " +
            "round(sum(calories),2) as sumCalories, " +
            "round(avg(avg_speed),2) as avgSpeed, " +
            "SEC_TO_TIME(AVG(TIME_TO_SEC(avg_pace))) as avgPace " +
            "from training " +
            "where calories > 0 " +
            "and user_id = :userId " +
            "and year(start_date) = :year " +
            "and month(start_date) = :month  and workout_type = :workoutType", nativeQuery = true)
    Object[] getMonthStatistic(Long userId, Long year, Long month, String workoutType);

    @Query(value = "select count(*) as traingingCount, " +
            "round(avg(distance),2) as avgDistance, " +
            "round(sum(distance),2) as sumDistance, " +
            "SEC_TO_TIME(AVG(TIME_TO_SEC(moving_time))) as avgMovingTime, " +
            "CAST(SEC_TO_TIME(sum(TIME_TO_SEC(moving_time))) as CHAR) as sumMovingTime, " +
            "round(avg(calories),2) as avgCalories, " +
            "round(sum(calories),2) as sumCalories, " +
            "round(avg(avg_speed),2) as avgSpeed, " +
            "SEC_TO_TIME(AVG(TIME_TO_SEC(avg_pace))) as avgPace " +
            "from training " +
            "where calories > 0 " +
            "and user_id = :userId " +
            "and year(start_date) = :year  and workout_type = :workoutType", nativeQuery = true)
    Object[] getYearStatistic(Long userId, Long year, String workoutType);

    @Query(value = "select count(*) as traingingCount, " +
            "round(avg(distance),2) as avgDistance, " +
            "round(sum(distance),2) as sumDistance, " +
            "SEC_TO_TIME(AVG(TIME_TO_SEC(moving_time))) as avgMovingTime, " +
            "CAST(SEC_TO_TIME(sum(TIME_TO_SEC(moving_time))) as CHAR) as sumMovingTime, " +
            "round(avg(calories),2) as avgCalories, " +
            "round(sum(calories),2) as sumCalories, " +
            "round(avg(avg_speed),2) as avgSpeed, " +
            "SEC_TO_TIME(AVG(TIME_TO_SEC(avg_pace))) as avgPace " +
            "from training " +
            "where calories > 0 " +
            "and user_id = :userId ", nativeQuery = true)
    Object[] getAllStatistic(Long userId);


    @Query(value = "select count(*) as traingingCount, " +
            "round(avg(distance),2) as avgDistance, " +
            "round(sum(distance),2) as sumDistance, " +
            "SEC_TO_TIME(AVG(TIME_TO_SEC(moving_time))) as avgMovingTime, " +
            "CAST(SEC_TO_TIME(sum(TIME_TO_SEC(moving_time))) as CHAR) as sumMovingTime, " +
            "round(avg(calories),2) as avgCalories, " +
            "round(sum(calories),2) as sumCalories, " +
            "round(avg(avg_speed),2) as avgSpeed, " +
            "SEC_TO_TIME(AVG(TIME_TO_SEC(avg_pace))) as avgPace " +
            "from training " +
            "where calories > 0 " +
            "and user_id = :userId " +
            "and training.id in (:trainingIds)", nativeQuery = true)
    Object[] getStatisticByTrainingId(Long userId, List<Long> trainingIds);

    @Query(value = "select max(calories) from training where user_id = userId", nativeQuery = true)
    Float getMaxCalories(Long userId);

    @Query(value = "select max(avg_speed) from training where user_id = userId", nativeQuery = true)
    Float getMaxSpeed(Long userId);

    @Query(value = "select min(avg_pace) from training where user_id = userId", nativeQuery = true)
    Float getMaxPace(Long userId);

    @Query(value = "select round(max(distance),2) from training where user_id = userId", nativeQuery = true)
    Float getMaxDistance(Long userId);

    @Query(value = "select max(moving_time) from training where user_id = userId", nativeQuery = true)
    LocalTime getMaxMovingTime(Long userId);


    @Query(value =
            "select max(calories), " +
                    " max(avg_speed), " +
                    " min(avg_pace), " +
                    " round(max(distance),2), " +
                    " max(moving_time) from training" +
                    " where user_id = :userId and workout_type = 'Run'", nativeQuery = true)
    Object[] getRunningRecords(Long userId);

    @Query(value =
            " select min(moving_time) from training where distance > 4.9 and distance < 5.2 and  workout_type = 'Run' and " +
                    " user_id = :userId ", nativeQuery = true)
    Object get5Km(Long userId);

    @Query(value =
            "  select min(moving_time) from training where distance > 9.9 and distance < 10.1 and " +
                    " user_id = :userId and workout_type = 'Run' ", nativeQuery = true)
    Object get10Km(Long userId);

    @Query(value =
            "select min(moving_time) from training where distance > 21.1 and distance < 21.4 and" +
                    " user_id = :userId and workout_type = 'Run' ", nativeQuery = true)
    Object get21Km(Long userId);

    @Query(value =
            "  select min(moving_time) from training where distance > 42 and distance < 43 and " +
                    " user_id = :userId and workout_type = 'Run' ", nativeQuery = true)
    Object get42Km(Long userId);


    @Query(value = "select max(calories), " +
                    " max(avg_speed), " +
                    " min(avg_pace), " +
                    " round(max(distance),2), " +
                    " max(moving_time) from training" +
                    " where user_id = :userId and workout_type = 'Cycling'", nativeQuery = true)
    Object[] getCyclingRecords(Long userId);


    @Query(value =
            " select min(moving_time) from training where distance > 9.9 and distance < 10.2 and  workout_type = 'Cycling' and " +
                    " user_id = :userId ", nativeQuery = true)
    Object get10KmC(Long userId);

    @Query(value =
            "  select min(moving_time) from training where distance > 24.5 and distance < 25.5 and " +
                    " user_id = :userId and workout_type = 'Cycling' ", nativeQuery = true)
    Object get25Km(Long userId);

    @Query(value =
            "select min(moving_time) from training where distance > 49.8 and distance < 50.4 and" +
                    " user_id = :userId and workout_type = 'Cycling' ", nativeQuery = true)
    Object get50Km(Long userId);

    @Query(value =
            "  select min(moving_time) from training where distance > 99 and distance < 101 and " +
                    " user_id = :userId and workout_type = 'Cycling' ", nativeQuery = true)
    Object get100Km(Long userId);


    @Query(value = "select max(calories), " +
            " max(avg_speed), " +
            " min(avg_pace), " +
            " round(max(distance),2), " +
            " max(moving_time) from training" +
            " where user_id = :userId and workout_type = 'Swim'", nativeQuery = true)
    Object[] getSwimRecords(Long userId);



    @Query(value = "  select min(moving_time) from training where distance > 0.04 and distance < 0.06 and " +
                    " user_id = :userId and workout_type = 'Swim' ", nativeQuery = true)
    Object get50m(Long userId);

    @Query(value = "  select min(moving_time) from training where distance > 0.09 and distance < 0.11 and " +
            " user_id = :userId and workout_type = 'Swim' ", nativeQuery = true)
    Object get100m(Long userId);

    @Query(value = "  select min(moving_time) from training where distance > 0.19 and distance < 0.21 and " +
            " user_id = :userId and workout_type = 'Swim' ", nativeQuery = true)
    Object get200m(Long userId);

    @Query(value = "  select min(moving_time) from training where distance > 0.39 and distance < 0.41 and " +
            " user_id = :userId and workout_type = 'Swim' ", nativeQuery = true)
    Object get400m(Long userId);


    @Query(value = "  select min(moving_time) from training where distance > 0.79 and distance < 0.81 and " +
            " user_id = :userId and workout_type = 'Swim' ", nativeQuery = true)
    Object get800m(Long userId);

    @Query(value = "  select min(moving_time) from training where distance > 1.49 and distance < 1.51 and " +
            " user_id = :userId and workout_type = 'Swim' ", nativeQuery = true)
    Object get1500m(Long userId);
}
