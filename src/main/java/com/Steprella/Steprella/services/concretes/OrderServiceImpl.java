package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.CalculationUtils;
import com.Steprella.Steprella.core.utils.EntityValidator;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.CartItem;
import com.Steprella.Steprella.entities.concretes.Customer;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final CustomerService customerService;
    private final CartItemService cartItemService;
    private final EntityValidator entityValidator;
    private final OrderItemService orderItemService;


    @Override
    public List<ListOrderResponse> getOrders(int page, int size) {
        Customer customer = customerService.getCustomerOfCurrentUser();
        return getOrdersForCustomer(customer, page, size);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ListOrderResponse> getOrdersByUserId(int userId, int page, int size) {
        Customer customer = customerService.getCustomerById(userId);
        return getOrdersForCustomer(customer, page, size);
    }

    @Override
    public ListOrderResponse getById(int id) {
        Order order = findByOrderId(id);
        return OrderMapper.INSTANCE.listResponseFromOrder(order);
    }

    @Override
    public Order getOrderById(int id) {
        return findOrderAndValidateOwnership(id);
    }

    @Override
    public AddOrderResponse add(AddOrderRequest request) {
        Customer customer = customerService.getCustomerOfCurrentUser();
        entityValidator.validateCustomerAddress(request.getShippingAddressId());

        boolean isValidCartItems = cartItemService.validateCartItems(request.getSelectedCartItemIds());
        if (!isValidCartItems) {
            throw new NotFoundException(Messages.Error.CUSTOM_CART_ITEMS_NOT_FOUND);
        }

        List<CartItem> cartItems = cartItemService.getCartItemsForOrder(request.getSelectedCartItemIds());
        
        cartItems.forEach(entityValidator::validateProductAvailabilityForOrder);

        Order addOrder = OrderMapper.INSTANCE.orderFromAddRequest(request, customer);
        addOrder.setOrderNumber(generateOrderNumber());
        addOrder.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(addOrder);

        List<OrderItem> orderItems = orderItemService.convertCartItemsToOrderItems(cartItems, savedOrder);
        orderItemService.saveOrderItems(orderItems);
        cartItemService.deleteCartItemsForOrder(request.getSelectedCartItemIds());

        return OrderMapper.INSTANCE.addResponseFromOrder(savedOrder);
    }

    @Override
    public UpdateOrderResponse update(UpdateOrderRequest request) {
        Order order = findByOrderId(request.getId());
        order.setStatus(request.getStatus());
        Order savedOrder = orderRepository.save(order);
        return OrderMapper.INSTANCE.updateResponseFromOrder(savedOrder);
    }

    @Override
    public void delete(int id) {
        Order order = findByOrderId(id);
        orderRepository.delete(order);
    }

    @Override
    public int getTotalCount() {
        Customer customer = customerService.getCustomerOfCurrentUser();
        return orderRepository.findByCustomerId(customer.getId()).size();
    }

    private Order findByOrderId(int id) {
        return  orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_ORDER_NOT_FOUND));
    }

    private Order findOrderAndValidateOwnership(int id) {
        Customer customer = customerService.getCustomerOfCurrentUser();
        return orderRepository.findByIdAndCustomerId(id, customer.getId())
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_ORDER_NOT_FOUND));
    }

    private List<ListOrderResponse> getOrdersForCustomer(Customer customer, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Order> orders = orderRepository.findByCustomerId(customer.getId(), pageable).getContent();

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
