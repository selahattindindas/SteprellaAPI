package com.Steprella.Steprella.services.dtos.responses.orders;

import com.Steprella.Steprella.services.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderResponse {

    private int id;

    private OrderStatus status;
}
