package com.Steprella.Steprella.services.dtos.requests.orderitems;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddOrderItemRequest {

    @NotNull
    private int orderId;

    @NotNull
    private int productVariantId;

    @NotNull
    private int productVariantSizeId;

    @NotNull
    private int quantity;
}
