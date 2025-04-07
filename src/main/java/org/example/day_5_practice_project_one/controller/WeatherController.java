package org.example.day_5_practice_project_one.controller;

import org.example.day_5_practice_project_one.model.WeatherResponse;
import org.example.day_5_practice_project_one.service.WeatherService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    public String getTemperature(@PathVariable String city) {
        Double temp = weatherService.getTemperature(city);
        return temp != null ? String.format("Current temperature in %s is %.2f°C", city, temp) : "Temperature data not available.";
    }

    @GetMapping("/pretty/{city}")
    public String getPrettyWeather(@PathVariable String city) {
        return weatherService.getPrettyWeatherInfo(city);
    }

}
