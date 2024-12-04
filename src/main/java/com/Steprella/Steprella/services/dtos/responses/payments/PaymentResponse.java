package com.Steprella.Steprella.services.dtos.responses.payments;

import com.Steprella.Steprella.services.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private int id;

    private Long orderId;

    private BigDecimal amount;

    private PaymentMethod method;

    private String cardNumber;

    private String cardHolderName;

    private String expirationDate;

    private String cvv;
}
