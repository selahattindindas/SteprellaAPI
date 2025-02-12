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


@Mapper(uses = ProductVariantMapper.class)
public interface CartItemMapper {

    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);

    @Mapping(target = "cartId", source = "cart.id")
    @Mapping(target = "productVariant", source = "productVariant")
    @Mapping(target = "productVariantSizeValue", source = "productSize.sizeValue")
    ListCartItemResponse listResponseFromCartItem(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cart.id", source = "cartId")
    @Mapping(target = "productVariant.id", source = "productVariantId")
    @Mapping(target = "productSize.id", source = "productVariantSizeId")
    CartItem cartItemFromAddRequest(AddCartItemRequest request);

    @Mapping(target = "cartId", source = "cart.id")
    @Mapping(target = "productVariantId", source = "productVariant.id")
    @Mapping(target = "productVariantSizeId", source = "productSize.id")
    AddCartItemResponse addResponseFromCartItem(CartItem cartItem);

    @Mapping(target = "cart.id", source = "cartId")
    @Mapping(target = "productVariant.id", source = "productVariantId")
    @Mapping(target = "productSize.id", source = "productVariantSizeId")
    CartItem cartItemFromUpdateRequest(UpdateCartItemRequest request);

    @Mapping(target = "cartId", source = "cart.id")
    @Mapping(target = "productVariantId", source = "productVariant.id")
    @Mapping(target = "productVariantSizeId", source = "productSize.id")
    UpdateCartItemResponse updateResponseFromCartItem(CartItem cartItem);
}



