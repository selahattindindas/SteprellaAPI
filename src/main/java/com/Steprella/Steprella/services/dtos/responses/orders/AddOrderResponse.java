package com.Steprella.Steprella.services.dtos.responses.orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddOrderResponse {

    private int id;

    private int userId;

    private int shippingAddressId;

    private String orderNumber;

    private List<Integer> cartItem;
}
