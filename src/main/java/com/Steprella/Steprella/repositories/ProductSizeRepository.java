package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.ProductSize;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSizeRepository extends BaseRepository<ProductSize, Integer> {
    List<ProductSize> findByProductVariantId(int productVariantId);
}
