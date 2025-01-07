package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.ShoeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoeModelRepository extends BaseRepository<ShoeModel, Integer> {

    Page<ShoeModel> findShoeModelByBrandId(int brandId, Pageable pageable);

    List<ShoeModel> findShoeModelByBrandId(int id);
}
