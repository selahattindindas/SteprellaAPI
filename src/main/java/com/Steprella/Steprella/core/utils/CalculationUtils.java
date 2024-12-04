package com.Steprella.Steprella.core.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

public class CalculationUtils {

    public static <T> BigDecimal calculateTotalPrice(List<T> items, Function<T, Integer> idMapper, Function<T, BigDecimal> priceMapper, int id) {
        return items.stream()
                .filter(item -> idMapper.apply(item) == id)
                .map(priceMapper)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static <T> int calculateTotalItems(List<T> items, Function<T, Integer> quantityMapper) {
        return items.stream()
                .map(quantityMapper)
                .reduce(0, Integer::sum);
    }
}
