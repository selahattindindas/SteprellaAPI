package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Rating;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends BaseRepository<Rating, Integer> {
}
