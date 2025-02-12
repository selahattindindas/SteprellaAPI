package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends BaseRepository<Order, Integer> {

    Page<Order> findByCustomerId(int customerId, Pageable pageable);

    List<Order> findByCustomerId(int customerId);

    Optional<Order> findByIdAndCustomerId(int id, int customerId);

    Optional<Order> findTopByOrderByIdDesc();
}
