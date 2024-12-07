package com.Steprella.Steprella.core.services;

import com.Steprella.Steprella.core.services.dtos.response.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final String weatherApiUrl = "https://api.openweathermap.org/data/2.5/weather";
    private final String iconWebUrl = "https://openweathermap.org/img/wn/";

    private final RestTemplate restTemplate;


    public WeatherResponse getWeatherByCoordinates(double lat, double lon) {
        String url = UriComponentsBuilder.fromHttpUrl(weatherApiUrl)
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", apiKey)
                .toUriString();

        WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);

        if (weatherResponse != null && weatherResponse.getMain() != null) {
            double kelvinTemp = weatherResponse.getMain().getTemp();
            double tempInCelsius = kelvinTemp - 273.15;

            double roundedTemp = Math.round(tempInCelsius * 10.0) / 10.0;
            weatherResponse.getMain().setTemp(roundedTemp);
        }

        if (weatherResponse != null && weatherResponse.getWeather() != null && weatherResponse.getWeather().length > 0) {
            String iconCode = weatherResponse.getWeather()[0].getIcon();
            String iconUrl = iconWebUrl + iconCode + ".png";
            weatherResponse.getWeather()[0].setIconUrl(iconUrl);
        }

        return weatherResponse;
    }
}
