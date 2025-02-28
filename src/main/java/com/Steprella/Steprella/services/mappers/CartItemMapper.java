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



