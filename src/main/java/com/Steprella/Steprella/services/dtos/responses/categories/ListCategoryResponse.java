package com.Steprella.Steprella.services.dtos.responses.categories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListCategoryResponse {

    private int id;

    private String name;

    private int parentId;

    private List<ListCategoryResponse> children;
}
