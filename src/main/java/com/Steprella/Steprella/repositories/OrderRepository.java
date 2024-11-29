package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Order;

import java.util.Optional;

public interface OrderRepository extends BaseRepository<Order, Integer> {

    Order findByUserId(int userId);

    Optional<Order> findTopByOrderByIdDesc();
}
