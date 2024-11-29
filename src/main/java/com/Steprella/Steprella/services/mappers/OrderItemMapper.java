package com.Steprella.Steprella.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderItemMapper {

    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);
}
