package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Color;
import com.Steprella.Steprella.services.dtos.responses.colors.ListColorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ColorMapper {

    ColorMapper INSTANCE = Mappers.getMapper(ColorMapper.class);

    ListColorResponse listResponseFromColor(Color color);
}
