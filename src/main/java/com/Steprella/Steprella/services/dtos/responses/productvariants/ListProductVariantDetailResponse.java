package com.Steprella.Steprella.services.dtos.responses.productvariants;

import com.Steprella.Steprella.services.dtos.responses.files.ListFileResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListProductVariantDetailResponse {
    private int variantId;

    private String colorName;

    private double rating;

    private int ratingCount;

    private String description;

    private String brandName;

    private String shoeModelName;

    private String materialName;

    private String usageAreaName;

    private List<ListFileResponse> productImages;
}
