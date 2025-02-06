package com.Steprella.Steprella.services.dtos.requests.products;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class ProductSearchCriteria {
    private Integer brandId;
    private Integer colorId;
    private Integer categoryId;
    private Integer sizeValue;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer materialId;
    private Integer usageAreaId;
    private List<Integer> featureIds;
    private String searchTerm;
    private int page;
    private int size;
}