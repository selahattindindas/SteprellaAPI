package com.Steprella.Steprella.services.dtos.requests.categories;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryRequest {

    @NotNull
    @Min(1)
    private int id;

    @NotBlank
    private String name;

    @NotNull
    private Integer parentId;
}
