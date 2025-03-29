package com.babycenter.pregnancy_tracker.service;

import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    public String getWeather(String location) {
        // Implement weather fetching logic
        return "Sunny";
    }
}