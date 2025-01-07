package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends BaseRepository<Order, Integer> {

    Page<Order> findOrderByUserId(int userId, Pageable pageable);

    List<Order> findOrderByUserId(int userId);

    Optional<Order> findTopByOrderByIdDesc();
}
