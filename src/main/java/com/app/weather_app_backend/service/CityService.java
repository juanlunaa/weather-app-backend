package com.app.weather_app_backend.service;

import com.app.weather_app_backend.exceptions.ResourceNotFoundException;
import com.app.weather_app_backend.model.City;
import com.app.weather_app_backend.repository.CityRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<City> getCitiesByName (String name) {
        return cityRepository.findCityByName(name);
    }

    public List<City> getCitiesByNameAndCountry (String nameCity, String codeCountry) {
        return cityRepository.findCityByNameAndCountry(nameCity, codeCountry);
    }

    public Map<String, Object> getLocalCityInfo (String ip, String API_KEY) {
        try {
            String API_URL = String.format("https://ipinfo.io/%s?token=%s", ip, API_KEY);
            String res = restTemplate.getForObject(API_URL, String.class);
            return new JSONObject(res).toMap();
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Location not found");
        }
    }
}
