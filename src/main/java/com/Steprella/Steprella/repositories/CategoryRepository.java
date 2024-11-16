package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE c.parent IS NULL")
    List<Category> findRootCategories();

    @Query(value = "select c from Category c where c.parent.id in (:parentId)")
    List<Category> findByParentId(@Param("parentId") Integer parentId);

    Optional<Category> findByNameIgnoreCase(String name);
}
