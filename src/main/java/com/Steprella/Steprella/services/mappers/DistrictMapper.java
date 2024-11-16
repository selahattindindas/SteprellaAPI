package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.District;
import com.Steprella.Steprella.services.dtos.responses.district.ListDistrictResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DistrictMapper {

    DistrictMapper INSTANCE = Mappers.getMapper(DistrictMapper.class);

    @Mapping(target = "cityId", source = "city.id")
    ListDistrictResponse listResponseFromDistrict(District district);
}
