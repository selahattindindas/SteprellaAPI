package com.Steprella.Steprella.services.dtos.responses.productvariants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductVariantResponse {

    private int id;

    private int colorId;

    private int productId;

    private String updatedDate;
}
