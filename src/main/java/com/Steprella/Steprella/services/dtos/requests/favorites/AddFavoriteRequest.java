package com.Steprella.Steprella.services.dtos.requests.favorites;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddFavoriteRequest {

    @NotNull
    private int productId;

    @NotNull
    private int userId;
}
