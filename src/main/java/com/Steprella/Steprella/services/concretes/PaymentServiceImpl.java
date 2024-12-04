package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.Order;
import com.Steprella.Steprella.entities.concretes.Payment;
import com.Steprella.Steprella.repositories.PaymentRepository;
import com.Steprella.Steprella.services.abstracts.OrderService;
import com.Steprella.Steprella.services.abstracts.PaymentService;
import com.Steprella.Steprella.services.dtos.requests.payments.PaymentRequest;
import com.Steprella.Steprella.services.dtos.responses.payments.PaymentResponse;
import com.Steprella.Steprella.services.mappers.PaymentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        Order order = orderService.getByResponseId(request.getOrderId());

        BigDecimal totalAmount = calculateOrderTotalAmount(order);

        Payment createdPayment = PaymentMapper.INSTANCE.paymentFromRequest(request);
        createdPayment.setAmount(totalAmount);
        createdPayment.setOrder(order);

        Payment savedPayment = paymentRepository.save(createdPayment);

        return PaymentMapper.INSTANCE.responseFromPayment(savedPayment);
    }

    private BigDecimal calculateOrderTotalAmount(Order order) {
        return order.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
