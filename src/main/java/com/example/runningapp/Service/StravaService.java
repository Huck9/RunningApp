package com.example.runningapp.Service;

import com.example.runningapp.Entity.StravaApi.StravaResponse;
import com.example.runningapp.Entity.StravaApi.StravaTraining;
import com.example.runningapp.Entity.Training;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class StravaService {

    @Value("${strava.client.id}")
    private String clientId;

    @Value(("${strava.client.secret}"))
    private String clientSecret;
    private static List<String> workoutTypes = List.of("Run", "Swim", "Cycling");
    private Map<String, List<Training>> trainingMap = new HashMap<>();

    public List<Training> getTrainings(String username) {
        return trainingMap.get(username);
    }

    public String getCodeRedirectUrl() {
        String url = "https://www.strava.com/oauth/authorize?client_id=" +
                clientId +
                "&response_type=code&redirect_uri=http://localhost/exchange_token&approval_prompt=force&scope=activity:read";
        return url;
    }

    public void getToken(String code) {
        String url = "https://www.strava.com/oauth/token?client_id="
                + clientId +
                "&client_secret=" +
                clientSecret +
                "&code=" +
                code +
                "&grant_type=authorization_code";

        WebClient webClient = WebClient.create(url);
        Mono<StravaResponse> response = webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).retrieve().bodyToMono(StravaResponse.class);
        getWorkouts(response.block().getAccessToken());
    }

    private void getWorkouts(String accessToken) {
        WebClient webClient = WebClient.create("https://www.strava.com/api/v3/athlete/activities");

        List<StravaTraining> trainings = new ArrayList<>();
        boolean isEnd = true;
        int page = 1;
        while (isEnd) {
            int finalPage = page;
            Mono<StravaTraining[]> response = webClient.get()
                    .uri(uriBuilder -> uriBuilder.queryParam("page", finalPage).build())
                    .headers(h -> h.setBearerAuth(accessToken))
                    .retrieve()
                    .bodyToMono(StravaTraining[].class);
            if (response.block() == null || page == 3) {
                isEnd = false;
            } else {
                trainings.addAll(List.of(response.block()));
            }
            page++;
        }
        processStravaTraining(trainings);
    }

    private void processStravaTraining(List<StravaTraining> stravaTrainings) {
        Random generator = new Random();
        List<Training> entityTraining = stravaTrainings.stream()
                .filter(t -> workoutTypes.contains(t.getType()))
                .map(t -> Training.builder().distance(t.getDistance()/1000)
                        .movingTime(LocalTime.ofSecondOfDay(t.getMoving_time()))
                        .name(t.getName())
                        .avgSpeed((float) (Math.round(t.getAverage_speed() * 3.6 * 100.0) / 100.0))
                        .avgPace(LocalTime.ofSecondOfDay((long) (t.getMoving_time() /(t.getDistance()/1000))))
                        .startDate(t.getStart_date())
                        .calories((float) (generator.nextInt(500) + 500))
                        .isImported(true)
                        .workoutType(t.getType())
                        .build())
                .collect(Collectors.toList());
        trainingMap.put(SecurityContextHolder.getContext().getAuthentication().getName(), entityTraining);
    }
}
