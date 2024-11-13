package com.Steprella.Steprella.services.dtos.responses.shoemodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListShoeModelResponse {

    private int id;

    private int brandId;

    private String name;
}
