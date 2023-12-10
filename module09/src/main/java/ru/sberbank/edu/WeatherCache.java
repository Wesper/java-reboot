package ru.sberbank.edu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Weather cache.
 */
@Component
public class WeatherCache {

    private final Map<String, WeatherInfo> cache = new HashMap<>();
    @Autowired
    private WeatherProvider weatherProvider;

    /**
     * Default constructor.
     */
    public WeatherCache() {
    }

    /**
     * Get ACTUAL weather info for current city or null if current city not found.
     * If cache doesn't contain weather info OR contains NOT ACTUAL info then we should download info
     * If you download weather info then you should set expiry time now() plus 5 minutes.
     * If you can't download weather info then remove weather info for current city from cache.
     *
     * @param city - city
     * @return actual weather info
     */
    public synchronized WeatherInfo getWeatherInfo(String city) {
        if (!cache.containsKey(city)) {
            WeatherInfo weatherInfo =  weatherProvider.get(city);
            if (weatherInfo != null) {
                cache.put(city, weatherInfo);
            }
            return weatherInfo;
        } else if (LocalDateTime.now().isEqual(cache.get(city).getExpiryTime()) || LocalDateTime.now().isBefore(cache.get(city).getExpiryTime())) {
            return cache.get(city);
        } else {
            WeatherInfo weatherInfo =  weatherProvider.get(city);
            if (weatherInfo == null) {
                removeWeatherInfo(city);
                return null;
            } else {
                cache.put(city, weatherInfo);
                return weatherInfo;
            }
        }
    }

    /**
     * Remove weather info from cache.
     **/
    public void removeWeatherInfo(String city) {
        cache.remove(city);
    }
}