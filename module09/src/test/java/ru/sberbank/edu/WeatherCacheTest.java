package ru.sberbank.edu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Map;

public class WeatherCacheTest {

    @Test
    public void wetherInfoWhenCacheNotContainsKeyAndGetDataTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext("ru.sberbank.edu");
        WeatherCache weatherCache = context.getBean(WeatherCache.class);

        WeatherInfo weatherInfo = weatherCache.getWeatherInfo("Ekaterinburg");
        Assertions.assertEquals(weatherInfo.getCity(), "Ekaterinburg");
        try {
            Field cache = weatherCache.getClass().getDeclaredField("cache");
            cache.setAccessible(true);
            Map<String, WeatherInfo> reflexCache = (Map<String, WeatherInfo>) cache.get(weatherCache);
            Assertions.assertEquals(1, reflexCache.size());
            Assertions.assertTrue(reflexCache.containsKey("Ekaterinburg"));
            Assertions.assertNotNull(reflexCache.get("Ekaterinburg"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Assertions.fail();
        }
    }

    @Test
    public void wetherInfoWhenCacheContainsKeyAndNotExpireTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext("ru.sberbank.edu");
        WeatherCache weatherCache = context.getBean(WeatherCache.class);
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(5);
        WeatherInfo weatherInfo = WeatherInfo.builder()
                .setCity("Ekaterinburg")
                .setDescription("overcast clouds")
                .setShortDescription("clouds")
                .setTemperature(100.00)
                .setFeelsLikeTemperature(99.00)
                .setPressure(100.00)
                .setWindSpeed(10)
                .setExpiryTime(localDateTime)
                .build();
        try {
            Field cache = weatherCache.getClass().getDeclaredField("cache");
            cache.setAccessible(true);
            Map<String, WeatherInfo> reflexCache = (Map<String, WeatherInfo>) cache.get(weatherCache);
            reflexCache.put("Ekaterinburg", weatherInfo);
            weatherCache.getWeatherInfo("Ekaterinburg");

            Assertions.assertEquals(1, reflexCache.size());
            Assertions.assertTrue(reflexCache.containsKey("Ekaterinburg"));
            Assertions.assertEquals(weatherInfo, reflexCache.get("Ekaterinburg"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Assertions.fail();
        }
    }

    @Test
    public void wetherInfoWhenCacheContainsKeyAndExpireTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext("ru.sberbank.edu");
        WeatherCache weatherCache = context.getBean(WeatherCache.class);
        LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(3);
        WeatherInfo weatherInfo = WeatherInfo.builder()
                .setCity("Ekaterinburg")
                .setDescription("overcast clouds")
                .setShortDescription("clouds")
                .setTemperature(100.00)
                .setFeelsLikeTemperature(99.00)
                .setPressure(100.00)
                .setWindSpeed(10)
                .setExpiryTime(localDateTime)
                .build();
        try {
            Field cache = weatherCache.getClass().getDeclaredField("cache");
            cache.setAccessible(true);
            Map<String, WeatherInfo> reflexCache = (Map<String, WeatherInfo>) cache.get(weatherCache);
            reflexCache.put("Ekaterinburg", weatherInfo);

            Thread.sleep(3_000);

            weatherCache.getWeatherInfo("Ekaterinburg");

            Assertions.assertEquals(1, reflexCache.size());
            Assertions.assertTrue(reflexCache.containsKey("Ekaterinburg"));
            Assertions.assertTrue(localDateTime.plusMinutes(5).isBefore(reflexCache.get("Ekaterinburg").getExpiryTime()));
        } catch (NoSuchFieldException | IllegalAccessException | InterruptedException e) {
            Assertions.fail();
        }
    }

    @Test
    public void wetherInfoWhenCacheNotContainsKeyAndNotGetDataTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext("ru.sberbank.edu");
        WeatherCache weatherCache = context.getBean(WeatherCache.class);
        try {
            Field cache = weatherCache.getClass().getDeclaredField("cache");
            cache.setAccessible(true);
            Map<String, WeatherInfo> reflexCache = (Map<String, WeatherInfo>) cache.get(weatherCache);

            weatherCache.getWeatherInfo("qwerty");

            Assertions.assertEquals(0, reflexCache.size());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Assertions.fail();
        }
    }

    @Test
    public void wetherInfoWhenCacheContainsKeyAndExpireAndNotGetDataTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext("ru.sberbank.edu");
        WeatherCache weatherCache = context.getBean(WeatherCache.class);
        LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(3);
        WeatherInfo weatherInfo = WeatherInfo.builder()
                .setCity("qwerty")
                .setDescription("overcast clouds")
                .setShortDescription("clouds")
                .setTemperature(100.00)
                .setFeelsLikeTemperature(99.00)
                .setPressure(100.00)
                .setWindSpeed(10)
                .setExpiryTime(localDateTime)
                .build();
        try {
            Field cache = weatherCache.getClass().getDeclaredField("cache");
            cache.setAccessible(true);
            Map<String, WeatherInfo> reflexCache = (Map<String, WeatherInfo>) cache.get(weatherCache);
            reflexCache.put("qwerty", weatherInfo);

            Thread.sleep(3_000);

            weatherCache.getWeatherInfo("qwerty");

            Assertions.assertEquals(0, reflexCache.size());
        } catch (NoSuchFieldException | IllegalAccessException | InterruptedException e) {
            Assertions.fail();
        }
    }
}
