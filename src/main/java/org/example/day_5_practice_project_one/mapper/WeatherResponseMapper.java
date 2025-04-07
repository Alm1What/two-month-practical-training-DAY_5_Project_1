package org.example.day_5_practice_project_one.mapper;

import org.example.day_5_practice_project_one.dto.WeatherDto;
import org.example.day_5_practice_project_one.model.Weather;
import org.example.day_5_practice_project_one.model.WeatherResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WeatherResponseMapper {

    @Mapping(source = "name", target = "city")
    @Mapping(source = "main.temp", target = "temp")
    @Mapping(source = "main.temp_min", target = "tempMin")
    @Mapping(source = "main.temp_max", target = "tempMax")
    @Mapping(source = "main.humidity", target = "humidity")
    @Mapping(source = "wind.speed", target = "windSpeed")
    @Mapping(target = "description", expression = "java(response.getWeather() != null && !response.getWeather().isEmpty() ? response.getWeather().get(0).getDescription() : null)")
    WeatherDto toDto(WeatherResponse response);
}
