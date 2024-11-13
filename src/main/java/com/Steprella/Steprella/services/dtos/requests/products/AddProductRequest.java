package com.Steprella.Steprella.services.dtos.requests.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequest {

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String description;

    @NotNull
    private int categoryId;

    @NotNull
    private int brandId;

    @NotNull
    private int shoeModelId;
}
