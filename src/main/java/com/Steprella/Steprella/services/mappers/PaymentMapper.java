package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Payment;
import com.Steprella.Steprella.services.dtos.requests.payments.PaymentRequest;
import com.Steprella.Steprella.services.dtos.responses.payments.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    Payment paymentFromRequest(PaymentRequest request);

    PaymentResponse responseFromPayment(Payment payment);
}
