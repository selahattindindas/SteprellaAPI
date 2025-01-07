package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends BaseRepository<CartItem, Integer> {

    Page<CartItem> findByCartId(int cartId, Pageable pageable);

    List<CartItem> findByCartId(int cartId);

    CartItem findByProductVariantIdAndCartId(int productVariantId, int cartId);

    Optional<CartItem> findByCartIdAndProductVariantIdAndProductSizeId(int cartId, int productVariantId, int productSizeId);
}
