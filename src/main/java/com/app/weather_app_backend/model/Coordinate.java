package com.app.weather_app_backend.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Coordinate {
    private Double lon;
    private Double lat;
}
