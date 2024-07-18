package com.app.weather_app_backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "cities")
public class City {
    @Id
    private Long id;
    private String name;
    private String state;
    private String country;
    private Coordinate coord;
}
