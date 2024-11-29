package com.Steprella.Steprella.core.utils;

import java.math.BigDecimal;
import java.util.List;

public class TotalAmountHelper {

    public interface Peaceable {
        BigDecimal getPrice();
        int getQuantity();
    }

    public static <T extends Peaceable> BigDecimal calculateTotalAmount(List<T> items) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (T item : items) {
            BigDecimal itemTotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        return totalAmount;
    }
}
