package com.Steprella.Steprella.services.dtos.responses.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductResponse {

    private int id;

    private BigDecimal price;

    private String description;

    private int categoryId;

    private int brandId;

    private int shoeModelId;

    private int materialId;

    private int usageAreaId;

    private List<Integer> featuresId;

    private String updatedDate;
}
