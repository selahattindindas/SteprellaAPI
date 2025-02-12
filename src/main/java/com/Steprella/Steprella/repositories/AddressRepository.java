package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Address;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends BaseRepository<Address, Integer> {

    List<Address> findByCustomerId(int customerId);
    boolean existsByIdAndCustomerId(int id, int customerId);
}
