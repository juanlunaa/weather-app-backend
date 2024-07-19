package com.app.weather_app_backend.service;

import com.app.weather_app_backend.model.City;
import com.app.weather_app_backend.repository.CityRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<City> getCitiesByName (String name) {
        return cityRepository.findCityByName(name);
    }

    public City getCitiesByNameAndCountry (String nameCity, String codeCountry) {
        return cityRepository.findCityByNameAndCountry(nameCity, codeCountry).get(0);
    }

    public Mono<Map<String, Object>> getLocalCityInfo (String ip, String API_KEY) {
        String API_URL = "https://ipinfo.io";
        WebClient webClient = webClientBuilder.baseUrl(API_URL).build();

        return webClient.get()
                .uri("/{ip}?token={key}", ip, API_KEY)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(WebClientResponseException.NotFound.class, ex ->
                        Mono.just(new JSONObject()
                            .put("message", "Location not found")
                            .toMap()));
    }
}
