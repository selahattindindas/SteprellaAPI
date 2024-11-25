package com.Steprella.Steprella.services.dtos.responses.cart_items;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartItemResponse {

    private int id;

    private int cartId;

    private int productVariantId;

    private int productVariantSizeId;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;
}
