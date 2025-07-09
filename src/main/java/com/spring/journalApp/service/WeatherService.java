package com.spring.journalApp.service;

import com.spring.journalApp.Cache.appCache;
import com.spring.journalApp.api.Response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apikey;

    @Autowired
    private appCache Appcache;

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getweather(String city) {
        // Safely get the API template from cache
        String template = Appcache.App_Cache.get("Weather_Api");
        if (template == null) {
            throw new IllegalStateException("Missing 'weather_api' config in MongoDB or cache not initialized.");
        }
        // Replace placeholders
        String finalApi = template
                .replace("CITY", city)
                .replace("API_KEY", apikey);

        // Make GET request to weather API
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(
                finalApi, HttpMethod.GET, null, WeatherResponse.class
        );

        return response.getBody();
    }
}
