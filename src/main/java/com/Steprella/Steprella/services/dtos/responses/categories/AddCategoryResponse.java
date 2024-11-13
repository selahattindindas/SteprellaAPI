package com.Steprella.Steprella.services.dtos.responses.categories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddCategoryResponse {

    private int id;

    private String name;

    private Integer parentId;
}
