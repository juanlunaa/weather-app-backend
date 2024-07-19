package com.app.weather_app_backend.service;

import com.app.weather_app_backend.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class WeatherService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono<Map<String, Object>> getWeatherApiResponse (Long cityId, String API_KEY) {
        String API_URL = String.format("https://api.openweathermap.org", cityId, API_KEY);
        WebClient webClient = webClientBuilder.baseUrl(API_URL).build();

        return webClient.get()
                .uri("/data/2.5/weather?id={cityId}&appid={key}", cityId, API_KEY)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> {
                    throw new ResourceNotFoundException("City not found");
                });
    }
}
