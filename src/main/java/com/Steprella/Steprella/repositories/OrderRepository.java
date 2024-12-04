package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends BaseRepository<Order, Integer> {

    List<Order> findOrderByUserId(int userId);

    Optional<Order> findTopByOrderByIdDesc();
}
