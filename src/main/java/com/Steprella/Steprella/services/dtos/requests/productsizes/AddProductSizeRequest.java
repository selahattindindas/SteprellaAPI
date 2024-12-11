package com.Steprella.Steprella.services.dtos.requests.productsizes;

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
public class AddProductSizeRequest {

    @NotNull
    @Min(1)
    private int productVariantId;

    @NotNull
    @Min(1)
    private int sizeValue;

    @NotNull
    @Min(1)
    private int stockQuantity;
}
