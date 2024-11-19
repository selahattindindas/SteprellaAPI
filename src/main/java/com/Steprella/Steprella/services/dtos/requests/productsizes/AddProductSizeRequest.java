package com.Steprella.Steprella.services.dtos.requests.productsizes;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductSizeRequest {

    @NotNull
    private int productVariantId;

    @NotNull
    private int sizeValue;

    @NotNull
    private int stockQuantity;
}
