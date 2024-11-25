package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.carts.AddCartRequest;
import com.Steprella.Steprella.services.dtos.responses.carts.AddCartResponse;
import com.Steprella.Steprella.services.dtos.responses.carts.ListCartResponse;

public interface CartService {

    ListCartResponse getCartByUserId(int userId);

    ListCartResponse getById(int id);

    AddCartResponse add(AddCartRequest request);

    void delete(int id);
}
