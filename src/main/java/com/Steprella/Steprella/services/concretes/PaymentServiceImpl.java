package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Order;
import com.Steprella.Steprella.entities.concretes.Payment;
import com.Steprella.Steprella.repositories.PaymentRepository;
import com.Steprella.Steprella.services.abstracts.BankCardService;
import com.Steprella.Steprella.services.abstracts.OrderService;
import com.Steprella.Steprella.services.abstracts.PaymentService;
import com.Steprella.Steprella.services.dtos.requests.payments.PaymentRequest;
import com.Steprella.Steprella.services.dtos.responses.payments.PaymentResponse;
import com.Steprella.Steprella.services.enums.OrderStatus;
import com.Steprella.Steprella.services.mappers.PaymentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BankCardService bankCardService;
    private final OrderService orderService;

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        Order order = orderService.getOrderById(request.getOrderId());
        validateOrderStatus(order);
        validatePaymentAmount(order, request.getAmount());
        bankCardService.getById(request.getBankCardId());

        try {
            return processPaymentTransaction(order, request);
        } catch (Exception e) {
            handlePaymentFailure(order, e);
            throw new BusinessException(String.format(Messages.Error.PAYMENT_ERROR, e.getMessage()));
        }
    }

    private void validateOrderStatus(Order order) {
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new BusinessException(Messages.Error.INVALID_ORDER_STATUS_FOR_PAYMENT);
        }
    }

    private void validatePaymentAmount(Order order, BigDecimal requestAmount) {
        BigDecimal totalAmount = calculateOrderTotalAmount(order);
        if (!totalAmount.equals(requestAmount)) {
            throw new BusinessException(Messages.Error.PAYMENT_AMOUNT_MISMATCH);
        }
    }


    private PaymentResponse processPaymentTransaction(Order order, PaymentRequest request) {
        boolean paymentSuccess = processPaymentWithProvider(request);

        if (!paymentSuccess) {
            orderService.updateOrderStatus(order.getId(), OrderStatus.CANCELLED);
            throw new BusinessException(Messages.Error.PAYMENT_PROCESSING_FAILED);
        }

        Payment payment = createPayment(order, request);
        orderService.updateOrderStatus(order.getId(), OrderStatus.CONFIRMED);

        return PaymentMapper.INSTANCE.responseFromPayment(payment);
    }

    private Payment createPayment(Order order, PaymentRequest request) {
        Payment payment = PaymentMapper.INSTANCE.paymentFromRequest(request);
        payment.setAmount(calculateOrderTotalAmount(order));
        payment.setOrder(order);
        return paymentRepository.save(payment);
    }

    private void handlePaymentFailure(Order order, Exception e) {
        orderService.updateOrderStatus(order.getId(), OrderStatus.CANCELLED);
    }

    private boolean processPaymentWithProvider(PaymentRequest request) {
        return true;
    }

    private BigDecimal calculateOrderTotalAmount(Order order) {
        return order.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
