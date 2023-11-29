package ru.sberbank.edu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.edu.model.WeatherInfoModel;

import java.time.LocalDateTime;

public class WeatherProvider {

    private RestTemplate restTemplate = null;
    private final int expiration = 5;
    private final String url = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";
    private final String apiKey = "061f7d4de15b8e59fcc916078e008eb5";

    /**
     * Download ACTUAL weather info from internet.
     * You should call GET http://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
     * You should use Spring Rest Template for calling requests
     *
     * @param city - city
     * @return weather info or null
     */
    public WeatherInfo get(String city) {
        String url = String.format(this.url, city, apiKey);
        restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            WeatherInfoModel weatherInfoModel;

            weatherInfoModel = objectMapper.readValue(response.getBody(), WeatherInfoModel.class);
            if (response.getStatusCode() != HttpStatus.OK || !weatherInfoModel.getCod().equals(200)) {
                return null;
            }
            return WeatherInfo.builder()
                    .setCity(city)
                    .setDescription(weatherInfoModel.getWeather().get(0).getDescription())
                    .setShortDescription(weatherInfoModel.getWeather().get(0).getMain())
                    .setTemperature(weatherInfoModel.getMain().getTemp())
                    .setFeelsLikeTemperature(weatherInfoModel.getMain().getFeelsLike())
                    .setPressure(weatherInfoModel.getMain().getPressure())
                    .setWindSpeed(weatherInfoModel.getWind().getSpeed())
                    .setExpiryTime(LocalDateTime.now().plusMinutes(expiration))
                    .build();
        } catch (HttpClientErrorException | JsonProcessingException e) {
            return null;
        }
    }
}
