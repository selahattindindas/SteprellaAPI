package com.Steprella.Steprella.services.dtos.responses.ratings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListRatingResponse {

    private int id;

    private int productId;

    private int rating;
}
