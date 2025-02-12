package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends BaseRepository<Customer, Integer> {
    Optional<Customer> findByUserId(int userId);
    boolean existsByUserId(int userId);
}