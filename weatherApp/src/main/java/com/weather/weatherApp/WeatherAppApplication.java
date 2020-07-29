package com.weather.weatherApp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableMongoRepositories("com.weather.weatherApp.repo")
public class WeatherAppApplication {
	private static final Logger LOGGER = LogManager.getLogger(WeatherAppApplication.class);

	@Value("${app.connect.timeout}")
	private int timeout;


	public static void main(String[] args) {
		SpringApplication.run(WeatherAppApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory ();
		LOGGER.debug("Connect timeout {}",timeout);
		factory.setConnectTimeout(timeout);
		factory.setReadTimeout(timeout);
		return new RestTemplate(factory);
	}


}
