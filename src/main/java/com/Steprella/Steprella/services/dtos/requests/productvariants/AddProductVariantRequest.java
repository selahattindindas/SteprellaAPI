package com.Steprella.Steprella.services.dtos.requests.productvariants;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductVariantRequest {

    @NotNull
    private int colorId;

    @NotNull
    private int productId;

    private Date createdDate;
}
