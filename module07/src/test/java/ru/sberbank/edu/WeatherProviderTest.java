package ru.sberbank.edu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class WeatherProviderTest {

    @Test
    public void getPositiveTest() {
        WeatherProvider weatherProvider = new WeatherProvider();
        WeatherInfo weatherInfo = weatherProvider.get("Ekaterinburg");
        Assertions.assertNotNull(weatherInfo);
    }

    @Test
    public void getNegativeTest() {
        WeatherProvider weatherProvider = new WeatherProvider();
        WeatherInfo weatherInfo = weatherProvider.get("qwerty");
        Assertions.assertNull(weatherInfo);
    }
}