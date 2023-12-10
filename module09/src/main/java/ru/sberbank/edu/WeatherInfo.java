package ru.sberbank.edu;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Weather info.
 */
public class WeatherInfo {

    private String city;

    /**
     * Short weather description
     * Like 'sunny', 'clouds', 'raining', etc
     */
    private String shortDescription;

    /**
     * Weather description.
     * Like 'broken clouds', 'heavy raining', etc
     */
    private String description;

    /**
     * Temperature.
     */
    private Double temperature;

    /**
     * Temperature that fells like.
     */
    private Double feelsLikeTemperature;

    /**
     * Wind speed.
     */
    private Double windSpeed;

    /**
     * Pressure.
     */
    private Double pressure;

    /**
     * Expiry time of weather info.
     * If current time is above expiry time then current weather info is not actual!
     */
    private LocalDateTime expiryTime;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private WeatherInfo weatherInfo = new WeatherInfo();

        public Builder setCity(String city) {
            weatherInfo.city = city;
            return this;
        }

        public Builder setShortDescription(String shortDescription) {
            weatherInfo.shortDescription = shortDescription;
            return this;
        }

        public Builder setDescription(String description) {
            weatherInfo.description = description;
            return this;
        }

        public Builder setTemperature(double temperature) {
            weatherInfo.temperature = temperature;
            return this;
        }

        public Builder setFeelsLikeTemperature(double feelsLikeTemperature) {
            weatherInfo.feelsLikeTemperature = feelsLikeTemperature;
            return this;
        }

        public Builder setWindSpeed(double windSpeed) {
            weatherInfo.windSpeed = windSpeed;
            return this;
        }

        public Builder setPressure(double pressure) {
            weatherInfo.pressure = pressure;
            return this;
        }

        public Builder setExpiryTime(LocalDateTime expiryTime) {
            weatherInfo.expiryTime = expiryTime;
            return this;
        }

        public WeatherInfo build() {
            if (weatherInfo.city == null && weatherInfo.description == null && weatherInfo.temperature == null
                    && weatherInfo.shortDescription == null && weatherInfo.expiryTime == null
                    && weatherInfo.feelsLikeTemperature == null && weatherInfo.pressure == null
                    && weatherInfo.windSpeed == null) {
                throw new IllegalArgumentException("All of the fields is not filled in");
            }
            return weatherInfo;
        }
    }

    public String getCity() {
        return city;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getFeelsLikeTemperature() {
        return feelsLikeTemperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Double getPressure() {
        return pressure;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "city='" + city + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", temperature=" + temperature +
                ", feelsLikeTemperature=" + feelsLikeTemperature +
                ", windSpeed=" + windSpeed +
                ", pressure=" + pressure +
                ", expiryTime=" + expiryTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherInfo that = (WeatherInfo) o;
        return Objects.equals(city, that.city) && Objects.equals(shortDescription, that.shortDescription) && Objects.equals(description, that.description) && Objects.equals(temperature, that.temperature) && Objects.equals(feelsLikeTemperature, that.feelsLikeTemperature) && Objects.equals(windSpeed, that.windSpeed) && Objects.equals(pressure, that.pressure) && Objects.equals(expiryTime, that.expiryTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, shortDescription, description, temperature, feelsLikeTemperature, windSpeed, pressure, expiryTime);
    }
}
