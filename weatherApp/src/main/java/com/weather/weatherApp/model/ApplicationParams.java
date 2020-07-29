package com.weather.weatherApp.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ApplicationParams {

    @Value("${darksky.net.api.key}")
    private String key;

    @Value("${darksky.net.api.url}")
    private String url;
}
