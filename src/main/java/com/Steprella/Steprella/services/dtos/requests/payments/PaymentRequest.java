package com.Steprella.Steprella.services.dtos.requests.payments;

import com.Steprella.Steprella.services.enums.CardType;
import com.Steprella.Steprella.services.enums.PaymentMethod;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    @NotNull
    private int orderId;

    @NotNull
    @DecimalMin(value = "1.00")
    private BigDecimal amount;

    @NotNull
    private PaymentMethod method;

    @NotNull
    private int bankCardId;

    @Size(min = 3, max = 3)
    private String cvv;

    private CardType cardType;
}
