package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.EntityValidator;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Order;
import com.Steprella.Steprella.repositories.OrderRepository;
import com.Steprella.Steprella.services.abstracts.AddressService;
import com.Steprella.Steprella.services.abstracts.OrderService;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.dtos.requests.orders.AddOrderRequest;
import com.Steprella.Steprella.services.dtos.requests.orders.UpdateOrderRequest;
import com.Steprella.Steprella.services.dtos.responses.orders.AddOrderResponse;
import com.Steprella.Steprella.services.dtos.responses.orders.ListOrderResponse;
import com.Steprella.Steprella.services.dtos.responses.orders.UpdateOrderResponse;
import com.Steprella.Steprella.services.enums.OrderStatus;
import com.Steprella.Steprella.services.mappers.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final AddressService addressService;
    private final EntityValidator entityValidator;

    @Override
    public ListOrderResponse getByUserId(int userId) {
        userService.getResponseById(userId);

        Order order = orderRepository.findByUserId(userId);

        return OrderMapper.INSTANCE.listResponseFromOrder(order);
    }

    @Override
    public ListOrderResponse getById(int id) {
        Order order = findOrderById(id);
        return OrderMapper.INSTANCE.listResponseFromOrder(order);
    }

    @Override
    public AddOrderResponse add(AddOrderRequest request) {
        userService.getResponseById(request.getUserId());
        addressService.getById(request.getShippingAddressId());
        entityValidator.validateUserAddress(request.getUserId(), request.getShippingAddressId());

        String orderNumber = generateOrderNumber();

        Order addOrder = OrderMapper.INSTANCE.orderFromAddRequest(request);
        addOrder.setOrderNumber(orderNumber);
        addOrder.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(addOrder);

        return OrderMapper.INSTANCE.addResponseFromOrder(savedOrder);
    }

    @Override
    public UpdateOrderResponse update(UpdateOrderRequest request) {
        findOrderById(request.getId());

        Order updatedOrder = OrderMapper.INSTANCE.orderFromUpdateRequest(request);
        Order savedOrder = orderRepository.save(updatedOrder);

        return OrderMapper.INSTANCE.updateResponseFromOrder(savedOrder);
    }

    @Override
    public void delete(int id) {
        Order order = findOrderById(id);
        orderRepository.delete(order);
    }

    private Order findOrderById(int id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_ORDER_NOT_FOUND));
    }

    private String generateOrderNumber() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String latestOrderNumber = orderRepository.findTopByOrderByIdDesc().map(Order::getOrderNumber).orElse(null);

        int nextOrderNumber = 1;
        if (latestOrderNumber != null) {
            String lastNumberPart = latestOrderNumber.substring(latestOrderNumber.length() - 4);
            nextOrderNumber = Integer.parseInt(lastNumberPart) + 1;
        }

        return "ORD-" + datePart + "-" + String.format("%04d", nextOrderNumber);
    }
}
