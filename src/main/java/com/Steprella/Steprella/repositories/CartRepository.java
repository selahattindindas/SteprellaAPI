package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Cart;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends BaseRepository<Cart, Integer>{

    Cart findByUserId(int userId);

    boolean existsByUserId(int userId);
}
