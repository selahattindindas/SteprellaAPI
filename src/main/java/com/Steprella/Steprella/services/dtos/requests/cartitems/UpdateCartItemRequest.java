package com.Steprella.Steprella.services.dtos.requests.cartitems;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartItemRequest {

    @NotNull
    @Min(1)
    private int id;

    @NotNull
    @Min(1)
    private int productVariantId;

    @NotNull
    @Min(1)
    private int productVariantSizeId;

    @NotNull
    @Min(1)
    private int quantity;
}
