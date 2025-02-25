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
    @Mapping(target = "variant.variantId", source = "productVariant.id")
    @Mapping(target = "variant.colorName", source = "productVariant.color.name")
    @Mapping(target = "variant.rating", source = "productVariant.product.rating")
    @Mapping(target = "variant.ratingCount", source = "productVariant.product.ratingCount")
    @Mapping(target = "variant.description", source = "productVariant.product.description")
    @Mapping(target = "variant.brandName", source = "productVariant.product.brand.name")
    @Mapping(target = "variant.shoeModelName", source = "productVariant.product.shoeModel.modelName")
    @Mapping(target = "variant.materialName", source = "productVariant.product.material.name")
    @Mapping(target = "variant.usageAreaName", source = "productVariant.product.usageArea.name")
    @Mapping(target = "variant.productImages", source = "productVariant.productFiles")
    ListOrderItemResponse listResponseFromOrderItem(OrderItem orderItem);
}
