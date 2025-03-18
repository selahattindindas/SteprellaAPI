package com.Steprella.Steprella.services.enums;

import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;

import java.util.Optional;

public enum CardType {
    VISA,
    MASTERCARD;

    public static CardType detectCardType(String cardNumber) {
        return Optional.of(cardNumber)
                .map(num -> num.startsWith("4") ? VISA : num.matches("^5[1-5].*") ? MASTERCARD : null)
                .orElseThrow(() -> new BusinessException("Kart numarası boş olamaz!"));
    }
}
