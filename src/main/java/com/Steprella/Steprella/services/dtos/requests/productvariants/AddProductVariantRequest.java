package com.Steprella.Steprella.services.dtos.requests.productvariants;

import jakarta.validation.constraints.Min;
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
    @Min(1)
    private int colorId;

    @NotNull
    @Min(1)
    private int productId;

    private Date createdDate;

    private boolean isActive;
}
