package com.Steprella.Steprella.services.dtos.requests.orders;

import com.Steprella.Steprella.services.enums.OrderStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderRequest {

    @NotNull
    @Min(1)
    private int id;

    @NotNull
    private OrderStatus status;
}
