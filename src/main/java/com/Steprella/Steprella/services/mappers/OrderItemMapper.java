package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.OrderItem;
import com.Steprella.Steprella.services.dtos.responses.orderitems.ListOrderItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = ProductVariantMapper.class)
public interface OrderItemMapper {

    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "productVariant", source = "productVariant")
    ListOrderItemResponse listResponseFromOrderItem(OrderItem orderItem);
}
