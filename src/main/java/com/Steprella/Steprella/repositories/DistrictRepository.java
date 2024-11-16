package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.District;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends BaseRepository<District, Integer> {

    List<District> findByCityId(int cityId);
}
