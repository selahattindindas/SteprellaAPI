package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Cart;
import com.Steprella.Steprella.entities.concretes.CartItem;
import com.Steprella.Steprella.services.dtos.requests.carts.AddCartRequest;
import com.Steprella.Steprella.services.dtos.responses.cart_items.ListCartItemResponse;
import com.Steprella.Steprella.services.dtos.responses.carts.AddCartResponse;
import com.Steprella.Steprella.services.dtos.responses.carts.ListCartResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = CartItemMapper.class)
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "cartItems", source = "cartItems")
    ListCartResponse listResponseFromCart(Cart cart);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    Cart cartFromAddRequest(AddCartRequest request);

    @Mapping(target = "userId", source = "user.id")
    AddCartResponse addResponseFrom(Cart cart);

    default List<ListCartItemResponse> mapCartItemsToDTO(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItemMapper.INSTANCE::listFromCartItem)
                .collect(Collectors.toList());
    }
}
