package com.Steprella.Steprella.services.dtos.responses.productsizes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductSizeResponse {

    private int id;

    private int productId;

    private int sizeValue;

    private int stockQuantity;
}
