package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Payment;
import com.Steprella.Steprella.services.dtos.requests.payments.PaymentRequest;
import com.Steprella.Steprella.services.dtos.responses.payments.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(target = "order.id", source = "orderId")
    @Mapping(target = "bankCard.id", source = "bankCardId")
    Payment paymentFromRequest(PaymentRequest request);

    @Mapping(target = "orderId", source = "order.id")
    PaymentResponse responseFromPayment(Payment payment);
}
