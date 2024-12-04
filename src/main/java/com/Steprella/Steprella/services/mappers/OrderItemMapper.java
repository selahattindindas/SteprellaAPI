package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.OrderItem;
import com.Steprella.Steprella.services.dtos.responses.orderitems.ListOrderItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderItemMapper {

    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "productVariant", source = "productVariant")
    @Mapping(target = "productVariant.price", source = "productVariant.product.price")
    @Mapping(target = "productVariant.description", source = "productVariant.product.description")
    @Mapping(target = "productVariant.colorName", source = "productVariant.color.name")
    @Mapping(target = "productVariant.brandName", source = "productVariant.product.brand.name")
    @Mapping(target = "productVariant.shoeModelName", source = "productVariant.product.shoeModel.modelName")
    @Mapping(target = "productVariantSizeValue", source = "productSize.sizeValue")
    ListOrderItemResponse listResponseFromOrderItem(OrderItem orderItem);
}
