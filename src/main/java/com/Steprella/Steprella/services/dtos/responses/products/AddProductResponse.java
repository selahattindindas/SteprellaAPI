package com.Steprella.Steprella.services.dtos.responses.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductResponse {

    private int id;

    private BigDecimal price;

    private int categoryId;

    private int brandId;

    private int shoeModelId;
}
