package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.CartItem;
import com.Steprella.Steprella.entities.concretes.Order;
import com.Steprella.Steprella.entities.concretes.OrderItem;
import com.Steprella.Steprella.entities.concretes.ProductSize;
import com.Steprella.Steprella.repositories.OrderItemRepository;
import com.Steprella.Steprella.services.abstracts.*;
import com.Steprella.Steprella.services.dtos.responses.orderitems.ListOrderItemResponse;
import com.Steprella.Steprella.services.mappers.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;
    private final ProductVariantService productVariantService;
    private final ProductSizeService productSizeService;

    @Autowired
    @Lazy
    public OrderItemServiceImpl(
            OrderItemRepository orderItemRepository,
            OrderService orderService,
            ProductVariantService productVariantService,
            ProductSizeService productSizeService
    ) {
        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
        this.productVariantService = productVariantService;
        this.productSizeService = productSizeService;
    }
    @Override
    public List<ListOrderItemResponse> getByOrderId(int orderId) {
        orderService.getById(orderId);

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        return orderItems.stream().map(orderItem -> {
            BigDecimal unitPrice = productVariantService.getUnitPriceByProductVariantId(orderItem.getProductVariant().getId());
            BigDecimal totalPrice = calculateTotalPrice(unitPrice, orderItem.getQuantity());

            orderItem.setUnitPrice(unitPrice);
            orderItem.setTotalPrice(totalPrice);

            return OrderItemMapper.INSTANCE.listResponseFromOrderItem(orderItem);
        }).collect(Collectors.toList());
    }

    @Override
    public ListOrderItemResponse getById(int id) {
        OrderItem orderItem = findOrderById(id);
        return OrderItemMapper.INSTANCE.listResponseFromOrderItem(orderItem);
    }

    @Override
    public List<OrderItem> convertCartItemsToOrderItems(List<CartItem> cartItems, Order savedOrder) {

        return cartItems.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            orderItem.setTotalPrice(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItem.setOrder(savedOrder);
            orderItem.setProductVariant(cartItem.getProductVariant());
            orderItem.setProductSize(cartItem.getProductSize());

            ProductSize productSize = cartItem.getProductSize();

            updateStockAndValidate(productSize, cartItem.getQuantity());

            return orderItem;
        }).collect(Collectors.toList());
    }

    @Override
    public void saveOrderItems(List<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);
    }

    private void updateStockAndValidate(ProductSize productSize, int quantity) {
        if (productSize.getStockQuantity() < quantity) {
            throw new BusinessException(String.format(Messages.Error.INSUFFICIENT_STOCK, quantity));
        }

        productSize.setStockQuantity(productSize.getStockQuantity() - quantity);

        productSizeService.save(productSize);
    }

    private BigDecimal calculateTotalPrice(BigDecimal unitPrice, int quantity) {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    private OrderItem findOrderById(int id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_ORDER_ITEM_NOT_FOUND));
    }
}
