package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Integer> {
}
