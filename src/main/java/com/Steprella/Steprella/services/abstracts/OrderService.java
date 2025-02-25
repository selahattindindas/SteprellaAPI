package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.entities.concretes.Order;
import com.Steprella.Steprella.services.dtos.requests.orders.AddOrderRequest;
import com.Steprella.Steprella.services.dtos.requests.orders.UpdateOrderRequest;
import com.Steprella.Steprella.services.dtos.responses.orders.AddOrderResponse;
import com.Steprella.Steprella.services.dtos.responses.orders.ListOrderResponse;
import com.Steprella.Steprella.services.dtos.responses.orders.UpdateOrderResponse;

import java.util.List;

public interface OrderService {

    List<ListOrderResponse> getOrders(int page, int size);

    List<ListOrderResponse> getOrdersByUserId(int userId, int page, int size);

    ListOrderResponse getById(int id);

    Order getOrderById(int id);

    AddOrderResponse add(AddOrderRequest request);

    UpdateOrderResponse update(UpdateOrderRequest request);

    void delete(int id);

    int getTotalCount();
}
