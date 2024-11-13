package com.Steprella.Steprella.services.dtos.requests.ratings;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRatingRequest {

    @NotNull
    private int id;

    @NotNull
    private int productId;

    @NotNull
    private int userId;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private int rating;
}
