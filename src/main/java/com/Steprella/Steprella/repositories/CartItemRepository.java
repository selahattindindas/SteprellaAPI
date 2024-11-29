package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.CartItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends BaseRepository<CartItem, Integer> {

    List<CartItem> findByCartId(int cartId);

    CartItem findByProductVariantIdAndCartId(int productVariantId, int cartId);
}
