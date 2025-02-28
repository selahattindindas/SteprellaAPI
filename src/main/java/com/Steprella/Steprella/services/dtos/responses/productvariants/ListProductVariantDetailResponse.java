package com.Steprella.Steprella.services.dtos.responses.productvariants;

import com.Steprella.Steprella.services.dtos.responses.files.ListFileResponse;
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
public class ListProductVariantDetailResponse {

    private int productId;

    private int variantId;

    private BigDecimal price;

    private double rating;

    private int ratingCount;

    private String colorName;

    private String brandName;

    private String shoeModelName;

    private String usageAreaName;

    private String description;

    private List<ListFileResponse> productImages;
}
