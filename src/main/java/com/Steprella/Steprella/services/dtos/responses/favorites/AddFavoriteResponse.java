package com.Steprella.Steprella.services.dtos.responses.favorites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddFavoriteResponse {

    private int id;

    private int productId;

    private int userId;
}
