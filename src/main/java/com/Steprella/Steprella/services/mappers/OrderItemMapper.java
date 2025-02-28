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
    @Mapping(target = "sizeValue", source = "productSize.sizeValue")
    @Mapping(target = "product.productId", source = "productVariant.product.id")
    @Mapping(target = "product.variantId", source = "productVariant.id")
    @Mapping(target = "product.price", source = "productVariant.product.price")
    @Mapping(target = "product.colorName", source = "productVariant.color.name")
    @Mapping(target = "product.rating", source = "productVariant.product.rating")
    @Mapping(target = "product.ratingCount", source = "productVariant.product.ratingCount")
    @Mapping(target = "product.description", source = "productVariant.product.description")
    @Mapping(target = "product.brandName", source = "productVariant.product.brand.name")
    @Mapping(target = "product.shoeModelName", source = "productVariant.product.shoeModel.modelName")
    @Mapping(target = "product.usageAreaName", source = "productVariant.product.usageArea.name")
    @Mapping(target = "product.productImages", source = "productVariant.productFiles")
    ListOrderItemResponse listResponseFromOrderItem(OrderItem orderItem);
}
