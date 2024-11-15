package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends BaseRepository<Address, Integer> {
}
