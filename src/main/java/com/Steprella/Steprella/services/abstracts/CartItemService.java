package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.cartitems.AddCartItemRequest;
import com.Steprella.Steprella.services.dtos.requests.cartitems.UpdateCartItemRequest;
import com.Steprella.Steprella.services.dtos.responses.cart_items.AddCartItemResponse;
import com.Steprella.Steprella.services.dtos.responses.cart_items.ListCartItemResponse;
import com.Steprella.Steprella.services.dtos.responses.cart_items.UpdateCartItemResponse;

import java.util.List;

public interface CartItemService {

    List<ListCartItemResponse> getItemsByCartId(int cartId);

    ListCartItemResponse getById(int id);

    AddCartItemResponse add(AddCartItemRequest request);

    UpdateCartItemResponse update(UpdateCartItemRequest request);

    void delete(int id);
}