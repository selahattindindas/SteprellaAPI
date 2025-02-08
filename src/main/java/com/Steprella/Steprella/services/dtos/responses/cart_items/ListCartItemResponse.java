package com.Steprella.Steprella.services.dtos.responses.cart_items;

import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListCartItemResponse {

    private int id;

    private int cartId;

    private boolean inStock;

    private int productVariantSizeValue;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    private ListProductVariantDetailResponse productVariant;
}
