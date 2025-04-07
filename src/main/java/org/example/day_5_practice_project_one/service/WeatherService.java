package org.example.day_5_practice_project_one.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.day_5_practice_project_one.dto.WeatherDto;
import org.example.day_5_practice_project_one.mapper.WeatherResponseMapper;
import org.example.day_5_practice_project_one.model.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final Dotenv dotenv = Dotenv.load();

    private final String apiKey = dotenv.get("WEATHER_API_KEY");
    private final String apiUrl = dotenv.get("OPEN_WEATHER_API_URL");

    private WeatherResponseMapper weatherResponseMapper;

    public WeatherService(WeatherResponseMapper weatherResponseMapper) {
        this.weatherResponseMapper = weatherResponseMapper;
    }


    public Double getTemperature(String city) {
        String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, city, apiKey);

        RestTemplate restTemplate = new RestTemplate();
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

        return response != null ? response.getMain().getTemp() : null;
    }

    public WeatherDto getWeatherDto(String city) {
        String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, city, apiKey);
        RestTemplate restTemplate = new RestTemplate();
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);
        return response != null ? weatherResponseMapper.toDto(response) : null;
    }

    public String getPrettyWeatherInfo(String city) {
        WeatherDto dto = getWeatherDto(city);
        if (dto == null) {
            return "Weather data not available.";
        }

        return String.format("""
            Погода у місті %s:
            🌡 Температура: %.1f°C (мін: %.1f°C / макс: %.1f°C)
            💧 Вологість: %d%%
            🌬 Вітер: %.1f м/с
            📖 Опис: %s
            """,
                dto.getCity(),
                dto.getTemp(),
                dto.getTempMin(),
                dto.getTempMax(),
                dto.getHumidity(),
                dto.getWindSpeed(),
                dto.getDescription()
        );
    }

}
