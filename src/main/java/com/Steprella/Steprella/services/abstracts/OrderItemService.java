package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.orderitems.AddOrderItemRequest;
import com.Steprella.Steprella.services.dtos.responses.orderitems.AddOrderItemResponse;
import com.Steprella.Steprella.services.dtos.responses.orderitems.ListOrderItemResponse;

import java.util.List;

public interface OrderItemService {

    List<ListOrderItemResponse> getByOrderId(int orderId);

    ListOrderItemResponse getById(int id);

    AddOrderItemResponse add(AddOrderItemRequest request);

    void delete(int id);
}
