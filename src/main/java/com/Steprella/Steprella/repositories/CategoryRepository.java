package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Category;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends BaseRepository<Category, Integer> {

    List<Category> findByParentIsNull();
}
