package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.City;
import com.Steprella.Steprella.services.dtos.responses.cities.ListCityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    ListCityResponse listResponseFromCity(City city);
}
