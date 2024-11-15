package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.services.abstracts.CityService;
import com.Steprella.Steprella.services.dtos.responses.cities.ListCityResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {

    @Override
    public List<ListCityResponse> getAll() {
        return List.of();
    }
}
