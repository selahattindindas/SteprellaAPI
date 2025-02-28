package com.Steprella.Steprella.services.dtos.responses.favorites;

import com.Steprella.Steprella.services.dtos.responses.products.ListProductResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListFavoriteResponse {

    private int id;

    private ListProductResponse product;
}
