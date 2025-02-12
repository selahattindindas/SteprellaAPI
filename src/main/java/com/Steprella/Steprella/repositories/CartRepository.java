package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Cart;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends BaseRepository<Cart, Integer> {
    
    Optional<Cart> findByCustomerId(int customerId);
    
    boolean existsByCustomerId(int customerId);
}
