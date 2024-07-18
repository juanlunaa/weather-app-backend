package com.app.weather_app_backend.service;

import com.app.weather_app_backend.exceptions.ResourceNotFoundException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, Object> getWeatherApiResponse (Integer cityId, String API_KEY) {
        try {
            String API_URL = String.format("https://api.openweathermap.org/data/2.5/weather?id=%s&appid=%s", cityId, API_KEY);
            String res = restTemplate.getForObject(API_URL, String.class);
            return new JSONObject(res).toMap();
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("City not found");
        }
    }
}
