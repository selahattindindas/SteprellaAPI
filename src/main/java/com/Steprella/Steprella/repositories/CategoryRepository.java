package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Integer> {
}
