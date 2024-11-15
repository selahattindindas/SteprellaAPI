package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.services.abstracts.RatingService;
import com.Steprella.Steprella.services.dtos.requests.ratings.AddRatingRequest;
import com.Steprella.Steprella.services.dtos.requests.ratings.UpdateRatingRequest;
import com.Steprella.Steprella.services.dtos.responses.ratings.AddRatingResponse;
import com.Steprella.Steprella.services.dtos.responses.ratings.ListRatingResponse;
import com.Steprella.Steprella.services.dtos.responses.ratings.UpdateRatingResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {

    @Override
    public List<ListRatingResponse> getRatingsByProductId(int productId) {
        return List.of();
    }

    @Override
    public ListRatingResponse getById(int id) {
        return null;
    }

    @Override
    public AddRatingResponse add(AddRatingRequest request) {
        return null;
    }

    @Override
    public UpdateRatingResponse update(UpdateRatingRequest updateRatingRequest) {
        return null;
    }
}
