package com.Steprella.Steprella.services.dtos.requests.customers;

import com.Steprella.Steprella.services.dtos.requests.users.AddUserRequest;
import com.Steprella.Steprella.services.dtos.responses.favorites.ListFavoriteResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddCustomerRequest extends AddUserRequest {

    private List<ListFavoriteResponse> favorites;
}
