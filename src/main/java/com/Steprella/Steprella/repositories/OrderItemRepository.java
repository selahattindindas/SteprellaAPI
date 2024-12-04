package com.Steprella.Steprella.repositories;

import com.Steprella.Steprella.entities.concretes.OrderItem;

import java.util.List;

public interface OrderItemRepository extends BaseRepository<OrderItem, Integer>{

    List<OrderItem> findByOrderId(int orderId);
}
