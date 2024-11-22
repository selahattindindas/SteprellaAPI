package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.ProductVariant;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends BaseRepository<ProductVariant, Integer> {

    boolean existsByColorIdAndProductId(int colorId, int productId);
}
