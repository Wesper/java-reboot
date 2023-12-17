package ru.sberbank.edu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.edu.model.WeatherInfoModel;

import java.time.LocalDateTime;

/**
 * Weather provider
 */
@Component
@PropertySource("classpath:application.properties")
public class WeatherProvider {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${apiKey}")
    private String apiKey;
    @Value("${weatherUrl}")
    private String url;
    @Value("${expiration}")
    private String expiration;

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
                    .setExpiryTime(LocalDateTime.now().plusMinutes(Long.parseLong(expiration)))
                    .build();
        } catch (HttpClientErrorException | JsonProcessingException e) {
            return null;
        }
    }
}
