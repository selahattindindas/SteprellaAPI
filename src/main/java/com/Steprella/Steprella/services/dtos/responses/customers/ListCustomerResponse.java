package com.Steprella.Steprella.services.dtos.responses.customers;

import com.Steprella.Steprella.services.dtos.responses.addresses.ListAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.carts.ListCartResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.ListCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.favorites.ListFavoriteResponse;
import com.Steprella.Steprella.services.dtos.responses.orders.ListOrderResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListCustomerResponse {

    private int id;

    private String fullName;

    private String email;

    private List<ListAddressResponse> addresses;

    private List<ListOrderResponse> orders;

    private List<ListCommentResponse> comments;

    private List<ListFavoriteResponse> favorites;

    private ListCartResponse cart;
}
