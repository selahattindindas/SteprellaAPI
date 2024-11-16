package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.responses.cities.ListCityResponse;

import java.util.List;

public interface CityService {

    List<ListCityResponse> getAll();

    ListCityResponse getById(int id);
}
