package com.Steprella.Steprella.services.dtos.responses.productsizes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListProductSizeResponse {

    private int id;

    private int sizeValue;

    private int stockQuantity;

    private boolean isActive;
}
