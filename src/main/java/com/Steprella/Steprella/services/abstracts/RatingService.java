package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.ratings.AddRatingRequest;
import com.Steprella.Steprella.services.dtos.requests.ratings.UpdateRatingRequest;
import com.Steprella.Steprella.services.dtos.responses.ratings.AddRatingResponse;
import com.Steprella.Steprella.services.dtos.responses.ratings.ListRatingResponse;
import com.Steprella.Steprella.services.dtos.responses.ratings.UpdateRatingResponse;

import java.util.List;

public interface RatingService {

    List<ListRatingResponse> getRatingsByProductId(int productId);

    ListRatingResponse getById(int id);

    AddRatingResponse add(AddRatingRequest request);

    UpdateRatingResponse update(UpdateRatingRequest updateRatingRequest);
}
