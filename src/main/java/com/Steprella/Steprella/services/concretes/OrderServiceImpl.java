package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.CalculationUtils;
import com.Steprella.Steprella.core.utils.EntityValidator;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.CartItem;
import com.Steprella.Steprella.entities.concretes.Order;
import com.Steprella.Steprella.entities.concretes.OrderItem;
import com.Steprella.Steprella.repositories.OrderRepository;
import com.Steprella.Steprella.services.abstracts.*;
import com.Steprella.Steprella.services.dtos.requests.orders.AddOrderRequest;
import com.Steprella.Steprella.services.dtos.requests.orders.UpdateOrderRequest;
import com.Steprella.Steprella.services.dtos.responses.orders.AddOrderResponse;
import com.Steprella.Steprella.services.dtos.responses.orders.ListOrderResponse;
import com.Steprella.Steprella.services.dtos.responses.orders.UpdateOrderResponse;
import com.Steprella.Steprella.services.enums.OrderStatus;
import com.Steprella.Steprella.services.mappers.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final AddressService addressService;
    private final CartItemService cartItemService;
    private final EntityValidator entityValidator;
    private final OrderItemService orderItemService;


    @Override
    public List<ListOrderResponse> getByUserId(int userId) {
        userService.getResponseById(userId);
        List<Order> orders = orderRepository.findOrderByUserId(userId);

        List<OrderItem> allOrderItems = orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.toList());

        return orders.stream().map(order -> {
            ListOrderResponse response = OrderMapper.INSTANCE.listResponseFromOrder(order);

            BigDecimal totalPriceForOrder = CalculationUtils.calculateTotalPrice(
                    allOrderItems,
                    orderItem -> orderItem.getOrder().getId(),
                    OrderItem::getTotalPrice,
                    order.getId()
            );

            response.setTotalPrice(totalPriceForOrder);

            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public ListOrderResponse getById(int id) {
        Order order = findOrderById(id);
        return OrderMapper.INSTANCE.listResponseFromOrder(order);
    }

    @Override
    public Order getByResponseId(int id) {
        return findOrderById(id);
    }

    @Override
    public AddOrderResponse add(AddOrderRequest request) {
        userService.getResponseById(request.getUserId());
        addressService.getById(request.getShippingAddressId());
        entityValidator.validateUserAddress(request.getUserId(), request.getShippingAddressId());

        boolean isValidCartItems = cartItemService.validateCartItems(request.getUserId(), request.getCartItem());
        if (!isValidCartItems) {
            throw new NotFoundException(Messages.Error.CUSTOM_CART_ITEMS_NOT_FOUND);
        }

        String orderNumber = generateOrderNumber();

        Order addOrder = OrderMapper.INSTANCE.orderFromAddRequest(request);
        addOrder.setOrderNumber(orderNumber);
        addOrder.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(addOrder);

        List<CartItem> cartItems = cartItemService.getCartItemsForUser(request.getUserId(), request.getCartItem());

        List<OrderItem> orderItems = orderItemService.convertCartItemsToOrderItems(cartItems, savedOrder);

        orderItemService.saveOrderItems(orderItems);

        cartItemService.deleteCartItemsForOrder(request.getUserId(), request.getCartItem());

        AddOrderResponse response = OrderMapper.INSTANCE.addResponseFromOrder(savedOrder);
        response.setCartItem(request.getCartItem());

        return response;
    }

    @Override
    public UpdateOrderResponse update(UpdateOrderRequest request) {
        Order order = findOrderById(request.getId());

        order.setStatus(request.getStatus());

        Order savedOrder = orderRepository.save(order);

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
