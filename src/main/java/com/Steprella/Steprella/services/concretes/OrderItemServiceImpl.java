package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.EntityValidator;
import com.Steprella.Steprella.entities.concretes.CartItem;
import com.Steprella.Steprella.entities.concretes.Order;
import com.Steprella.Steprella.entities.concretes.OrderItem;
import com.Steprella.Steprella.repositories.OrderItemRepository;
import com.Steprella.Steprella.services.abstracts.*;
import com.Steprella.Steprella.services.dtos.requests.orderitems.AddOrderItemRequest;
import com.Steprella.Steprella.services.dtos.responses.orderitems.AddOrderItemResponse;
import com.Steprella.Steprella.services.dtos.responses.orderitems.ListOrderItemResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
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
        entityValidator.checkProductVariantAvailability(request.getProductVariantId(), request.getProductVariantSizeId(), request.getQuantity());
        return null;
    }

    @Override
    public void delete(int id) {

    }


    public List<OrderItem> convertCartItemsToOrderItems(List<CartItem> cartItems, Order savedOrder) {
        return cartItems.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();

            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            orderItem.setTotalPrice(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItem.setOrder(savedOrder);
            orderItem.setProductVariant(cartItem.getProductVariant());
            orderItem.setProductSize(cartItem.getProductSize());

            return orderItem;
        }).collect(Collectors.toList());
    }

    public void saveOrderItems(List<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);
    }
}
