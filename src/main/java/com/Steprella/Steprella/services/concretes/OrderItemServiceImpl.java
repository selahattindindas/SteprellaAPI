package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.EntityValidator;
import com.Steprella.Steprella.repositories.OrderItemRepository;
import com.Steprella.Steprella.services.abstracts.*;
import com.Steprella.Steprella.services.dtos.requests.orderitems.AddOrderItemRequest;
import com.Steprella.Steprella.services.dtos.responses.orderitems.AddOrderItemResponse;
import com.Steprella.Steprella.services.dtos.responses.orderitems.ListOrderItemResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;
    private final ProductVariantService productVariantService;
    private final EntityValidator entityValidator;

    @Override
    public List<ListOrderItemResponse> getByOrderId(int orderId) {
        return List.of();
    }

    @Override
    public ListOrderItemResponse getById(int id) {
        return null;
    }

    @Override
    public AddOrderItemResponse add(AddOrderItemRequest request) {
        orderService.getById(request.getOrderId());
        productVariantService.getById(request.getProductVariantId());
        entityValidator.checkProductVariantAvailability(request.getProductVariantId(), request.getProductVariantSizeId(), request.getQuantity());
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
