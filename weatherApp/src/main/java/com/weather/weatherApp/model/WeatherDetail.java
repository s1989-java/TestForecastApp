package com.weather.weatherApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashMap;

@Document(collection = "weatherDetail")
@Getter
@Setter
public class WeatherDetail {

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("currently")
    private LinkedHashMap<String,Object> currently;

    @JsonProperty("minutely")
    private LinkedHashMap<String,Object> minutely;

    @JsonProperty("hourly")
    private LinkedHashMap<String,Object> hourly;

    @JsonProperty("daily")
    private LinkedHashMap<String,Object> daily;

    @JsonProperty("flags")
    private LinkedHashMap<String,Object> flags;

    @JsonProperty("offset")
    private int offset;

}
