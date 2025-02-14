package com.Steprella.Steprella.services.dtos.requests.products;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequest {

    @NotNull
    @DecimalMin(value = "1.00")
    private BigDecimal price;

    @NotBlank
    private String description;

    @NotNull
    @Min(1)
    private int categoryId;

    @NotNull
    @Min(1)
    private int brandId;

    @NotNull
    @Min(1)
    private int shoeModelId;

    @NotNull
    @Min(1)
    private int materialId;

    @NotNull
    @Min(1)
    private int usageAreaId;

    @NotNull(message = "Features list cannot be null")
    @Size(min = 1, message = "At least one feature must be selected")
    private List<@Min(1) Integer> featuresId;

    private Date createdDate;
}
