package ru.sberbank.edu;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class WeatherCache {

    private final Map<String, WeatherInfo> cache = new HashMap<>();
    private final WeatherProvider weatherProvider;

    /**
     * Constructor.
     *
     * @param weatherProvider - weather provider
     */
    public WeatherCache(WeatherProvider weatherProvider) {
        this.weatherProvider = weatherProvider;
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
    private void removeWeatherInfo(String city) {
        cache.remove(city);
    }
}
