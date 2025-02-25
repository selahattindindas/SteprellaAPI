package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.CartItem;
import com.Steprella.Steprella.services.dtos.requests.cartitems.AddCartItemRequest;
import com.Steprella.Steprella.services.dtos.requests.cartitems.UpdateCartItemRequest;
import com.Steprella.Steprella.services.dtos.responses.cart_items.AddCartItemResponse;
import com.Steprella.Steprella.services.dtos.responses.cart_items.ListCartItemResponse;
import com.Steprella.Steprella.services.dtos.responses.cart_items.UpdateCartItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CartItemMapper {

    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);

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
    @Mapping(target = "sizeId", source = "productSize.id")
    @Mapping(target = "sizeValue", source = "productSize.sizeValue")
    ListCartItemResponse listResponseFromCartItem(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productVariant.id", source = "productVariantId")
    @Mapping(target = "productSize.id", source = "productVariantSizeId")
    CartItem cartItemFromAddRequest(AddCartItemRequest request);

    @Mapping(target = "productVariantId", source = "productVariant.id")
    @Mapping(target = "productVariantSizeId", source = "productSize.id")
    AddCartItemResponse addResponseFromCartItem(CartItem cartItem);

    @Mapping(target = "productVariant.id", source = "productVariantId")
    @Mapping(target = "productSize.id", source = "productVariantSizeId")
    CartItem cartItemFromUpdateRequest(UpdateCartItemRequest request);

    @Mapping(target = "productVariantId", source = "productVariant.id")
    @Mapping(target = "productVariantSizeId", source = "productSize.id")
    UpdateCartItemResponse updateResponseFromCartItem(CartItem cartItem);
}



