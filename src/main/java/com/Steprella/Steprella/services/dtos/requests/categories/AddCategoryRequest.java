package com.Steprella.Steprella.services.dtos.requests.categories;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddCategoryRequest {

    @NotBlank
    private String name;

    private Integer parentId;
}
