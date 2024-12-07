package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.services.WeatherService;
import com.Steprella.Steprella.core.services.dtos.response.WeatherResponse;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weathers")
@AllArgsConstructor
public class WeatherController extends BaseController{

    private final WeatherService weatherService;

    @GetMapping("/api/weather")
    public ResponseEntity<BaseResponse<WeatherResponse>> getWeather(
            @RequestParam double lat,
            @RequestParam double lon) {

        WeatherResponse weather = weatherService.getWeatherByCoordinates(lat, lon);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, weather);
    }
}
