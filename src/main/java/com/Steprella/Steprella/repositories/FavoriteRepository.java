package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends BaseRepository<Favorite, Integer> {

    Page<Favorite> findByCustomerId(int customerId, Pageable pageable);

    List<Favorite> findByCustomerId(int customerId);

    boolean existsByCustomerIdAndProductVariantId(int customerId, int productVariantId);

    @Query(value = "SELECT f.product_variant_id FROM favorites f WHERE f.customer_id = :customerId", nativeQuery = true)
    List<Integer> findProductVariantIdsByCustomerId(@Param("customerId") int customerId);
}
