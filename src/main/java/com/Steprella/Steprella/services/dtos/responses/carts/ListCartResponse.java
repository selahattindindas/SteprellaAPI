package com.Steprella.Steprella.services.dtos.responses.carts;

import com.Steprella.Steprella.services.dtos.responses.cart_items.ListCartItemResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListCartResponse {

    private int id;

    private BigDecimal totalPrice;

    private int totalItems;

    private List<ListCartItemResponse> cartItems;
}
