package com.Steprella.Steprella.services.dtos.responses.bankcards;

import com.Steprella.Steprella.services.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListBankCardResponse {

    private int id;

    private String title;

    private String cardNumber;

    private String cardHolderName;

    private String expirationDate;

    private String cvv;

    private CardType cardType;
}
