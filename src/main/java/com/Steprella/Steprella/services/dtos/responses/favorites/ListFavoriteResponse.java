package com.Steprella.Steprella.services.dtos.responses.favorites;

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

    private ListFavoriteProductResponse productVariant;
}
