package org.example.day_5_practice_project_one.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.day_5_practice_project_one.model.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final Dotenv dotenv = Dotenv.load();

    private final String apiKey = dotenv.get("WEATHER_API_KEY");
    private final String apiUrl = dotenv.get("OPEN_WEATHER_API_URL");



    public Double getTemperature(String city) {
        String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, city, apiKey);

        RestTemplate restTemplate = new RestTemplate();
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

        return response != null ? response.getMain().getTemp() : null;
    }

    public WeatherResponse getAllInfoOfCityWeather(String city) {
        String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, city, apiKey);
        RestTemplate restTemplate = new RestTemplate();
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);
        return response;
    }

    public String getPrettyWeatherInfo(String city) {
        WeatherResponse response = getAllInfoOfCityWeather(city);
        if (response == null) {
            return "Weather data not available.";
        }

        return String.format("""
            Погода у місті %s:
            🌡 Температура: %.1f°C (мін: %.1f°C / макс: %.1f°C)
            💧 Вологість: %d%%
            🌬 Вітер: %.1f м/с
            📖 Опис: %s
            """,
                response.getName(),
                response.getMain().getTemp(),
                response.getMain().getTemp_min(),
                response.getMain().getTemp_max(),
                response.getMain().getHumidity(),
                response.getWind().getSpeed(),
                response.getWeather().get(0).getDescription()
        );
    }

}
