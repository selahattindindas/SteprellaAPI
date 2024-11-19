package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.ProductFile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductFileRepository extends BaseRepository<ProductFile, Integer> {

    List<ProductFile> findByProductVariantId(int productVariant);
}
