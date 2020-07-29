package com.weather.weatherApp.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Countries {

    private String name;
    private String latitude;
    private String longitude;

    public Countries(String name, String latitude, String longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
