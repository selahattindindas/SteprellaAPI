package com.Steprella.Steprella.services.dtos.requests.payments;

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
    @Min(1)
    private int orderId;

    @NotNull
    @DecimalMin(value = "1.00")
    private BigDecimal amount;

    @NotNull
    private PaymentMethod method;

    @NotBlank
    @Size(min = 16, max = 16)
    private String cardNumber;

    @NotBlank
    private String cardHolderName;

    @NotBlank
    @Size(min = 5, max = 5)
    private String expirationDate;

    @NotBlank
    @Size(min = 3, max = 3)
    private String cvv;
}
