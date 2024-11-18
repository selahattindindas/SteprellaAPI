package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.ShoeModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoeModelRepository extends BaseRepository<ShoeModel, Integer> {

    List<ShoeModel> findShoeModelByBrandId(int id);
}
