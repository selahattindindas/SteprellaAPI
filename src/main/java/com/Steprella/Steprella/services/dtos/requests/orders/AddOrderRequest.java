package com.Steprella.Steprella.services.dtos.requests.orders;
import jakarta.validation.constraints.Min;
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
    @Min(1)
    private int shippingAddressId;

    @NotNull
    private List<Integer> cartItem;
}
