package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.entities.concretes.Cart;
import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.services.dtos.responses.carts.AddCartResponse;
import com.Steprella.Steprella.services.dtos.responses.carts.ListCartResponse;

public interface CartService {

    ListCartResponse getCart();

    AddCartResponse createCart(Customer customer);

    Cart getById(int id);
}
