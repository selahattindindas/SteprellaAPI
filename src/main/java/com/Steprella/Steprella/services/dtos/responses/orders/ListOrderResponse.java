package com.Steprella.Steprella.services.dtos.responses.orders;

import com.Steprella.Steprella.services.dtos.responses.addresses.ListAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.orderitems.ListOrderItemResponse;
import com.Steprella.Steprella.services.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListOrderResponse {

    private int id;

    private BigDecimal totalPrice;

    private String orderNumber;

    private OrderStatus status;

    private ListAddressResponse shippingAddress;

    private List<ListOrderItemResponse> items;
}
