package com.Steprella.Steprella.core.services.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {

    private Weather[] weather;
    private Main main;
    private String name;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Weather {
        private String icon;
        private String iconUrl;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Main {
        private double temp;

    }
}
