package com.Steprella.Steprella.services.dtos.responses.orderitems;

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
public class ListOrderItemResponse {

    private int id;

    private int orderId;

    private int sizeValue;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    private ListProductVariantDetailResponse variant;
}
