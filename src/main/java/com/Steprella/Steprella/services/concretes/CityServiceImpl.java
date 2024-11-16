package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.City;
import com.Steprella.Steprella.repositories.CityRepository;
import com.Steprella.Steprella.services.abstracts.CityService;
import com.Steprella.Steprella.services.dtos.responses.cities.ListCityResponse;
import com.Steprella.Steprella.services.mappers.CityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {

        private CityRepository cityRepository;

    @Override
    public List<ListCityResponse> getAll() {
      List<City> cities = cityRepository.findAll();
      return cities.stream().map(CityMapper.INSTANCE::listResponseFromCity).collect(Collectors.toList());
    }

    @Override
    public ListCityResponse getById(int id) {
        City city = cityRepository.findById(id).orElse(null);
        return CityMapper.INSTANCE.listResponseFromCity(city);
    }
}
