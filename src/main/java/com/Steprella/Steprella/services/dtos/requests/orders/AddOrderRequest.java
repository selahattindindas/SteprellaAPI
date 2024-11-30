package com.Steprella.Steprella.services.dtos.requests.orders;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddOrderRequest {

    @NotNull
    private int userId;

    @NotNull
    private int shippingAddressId;

    private List<Integer> cartItem;
}
