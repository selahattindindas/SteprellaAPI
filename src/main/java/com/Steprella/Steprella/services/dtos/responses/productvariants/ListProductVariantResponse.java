package com.Steprella.Steprella.services.dtos.responses.productvariants;

import com.Steprella.Steprella.services.dtos.responses.files.ListFileResponse;
import com.Steprella.Steprella.services.dtos.responses.productsizes.ListProductSizeResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListProductVariantResponse {

    private int id;

    private String colorName;

    private boolean isActive;

    private boolean isFavorite;

    private List<ListProductSizeResponse> productSizes;

    private List<ListFileResponse> productFiles;

    private String createdDate;

    private String updatedDate;
}
