package com.Steprella.Steprella.services.dtos.requests.orders;

import com.Steprella.Steprella.services.enums.OrderStatus;
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
    private int id;

    @NotNull
    private OrderStatus status;
}
