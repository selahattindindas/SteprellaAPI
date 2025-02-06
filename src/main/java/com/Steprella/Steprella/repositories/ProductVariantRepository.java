package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepository extends BaseRepository<ProductVariant, Integer>, JpaSpecificationExecutor<ProductVariant> {

    boolean existsByColorIdAndProductId(int colorId, int productId);

    List<ProductVariant> findByIsActiveTrue();

    Page<ProductVariant> findByIsActiveTrue(Pageable pageable);

    List<ProductVariant> findByProductId(int productId);
}
