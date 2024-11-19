package com.Steprella.Steprella.services.dtos.responses.products;

import com.Steprella.Steprella.services.dtos.responses.comments.ListCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.productsizes.ListProductSizeResponse;
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
public class ListProductResponse {

    private int id;

    private BigDecimal price;

    private String description;

    private String categoryName;

    private String brandName;

    private String shoeModelName;

    private String createdDate;

    private String updatedDate;
}
