package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Cart;
import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.services.dtos.responses.carts.AddCartResponse;
import com.Steprella.Steprella.services.dtos.responses.carts.ListCartResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = CartItemMapper.class)
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "totalItems", source = "totalItems")
    @Mapping(target = "cartItems", source = "cartItems")
    ListCartResponse listResponseFromCart(Cart cart);

    @Mapping(target = "customer", source = "customer")
    Cart createCart(Customer customer);

    AddCartResponse addResponseFromCart(Cart cart);
}
