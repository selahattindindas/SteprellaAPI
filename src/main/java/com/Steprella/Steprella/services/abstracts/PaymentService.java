package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.payments.PaymentRequest;
import com.Steprella.Steprella.services.dtos.responses.payments.PaymentResponse;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest request);
}
