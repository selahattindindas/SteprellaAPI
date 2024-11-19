package com.Steprella.Steprella.services.dtos.responses.productsizes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductSizeResponse {

    private int id;

    private int productVariantId;

    private int sizeValue;

    private int stockQuantity;
}
