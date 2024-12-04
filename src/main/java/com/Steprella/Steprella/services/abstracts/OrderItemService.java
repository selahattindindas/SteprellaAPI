package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.entities.concretes.CartItem;
import com.Steprella.Steprella.entities.concretes.Order;
import com.Steprella.Steprella.entities.concretes.OrderItem;
import com.Steprella.Steprella.services.dtos.responses.orderitems.ListOrderItemResponse;

import java.util.List;

public interface OrderItemService {

    List<ListOrderItemResponse> getByOrderId(int orderId);

    ListOrderItemResponse getById(int id);

    List<OrderItem> convertCartItemsToOrderItems(List<CartItem> cartItems, Order savedOrder);

    void saveOrderItems(List<OrderItem> orderItems);
}
