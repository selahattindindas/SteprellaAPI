package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Favorite;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends BaseRepository<Favorite, Integer> {
    List<Favorite> findByUserId(int userId);

    boolean existsByUserIdAndProductVariantId(int userId, int productVariantId);
}
