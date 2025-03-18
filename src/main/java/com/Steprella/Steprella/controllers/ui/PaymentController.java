package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.PaymentService;
import com.Steprella.Steprella.services.dtos.requests.payments.PaymentRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.payments.PaymentResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController extends BaseController {
    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<BaseResponse<PaymentResponse>> processPayment(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse payment = paymentService.processPayment(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, payment);
    }
}
