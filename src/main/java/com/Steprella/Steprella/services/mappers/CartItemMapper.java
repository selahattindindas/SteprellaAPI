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

    @Mapping(target = "cartId", source = "cart.id")
    @Mapping(target = "productVariant", source = "productVariant")
    @Mapping(target = "productVariant.price", source = "productVariant.product.price")
    @Mapping(target = "productVariant.description", source = "productVariant.product.description")
    @Mapping(target = "productVariant.colorName", source = "productVariant.color.name")
    @Mapping(target = "productVariant.brandName", source = "productVariant.product.brand.name")
    @Mapping(target = "productVariant.shoeModelName", source = "productVariant.product.shoeModel.modelName")
    @Mapping(target = "productVariantSizeValue", source = "productSize.sizeValue")
    ListCartItemResponse listFromCartItem(CartItem cartItem);

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



