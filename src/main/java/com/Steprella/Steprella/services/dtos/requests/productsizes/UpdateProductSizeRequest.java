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
public class UpdateProductSizeRequest {

    @NotNull
    private int id;

    @NotNull
    private int productId;

    @NotNull
    private int sizeId;

    @NotNull
    private int stockQuantity;
}
