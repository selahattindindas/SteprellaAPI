package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Favorite;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends BaseRepository<Favorite, Integer> {
}
