package com.Steprella.Steprella.services.dtos.requests.bankcards;

import com.Steprella.Steprella.services.enums.CardType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBankCardRequest {

    @NotNull
    @Min(1)
    private int id;

    @NotBlank
    private String title;

    @NotBlank
    @Size(min = 16, max = 16)
    private String cardNumber;

    @NotBlank
    private String cardHolderName;

    @NotBlank
    private String expirationDate;

    @NotBlank
    @Size(min = 3, max = 3)
    private String cvv;

    @NotNull
    private CardType cardType;
}
