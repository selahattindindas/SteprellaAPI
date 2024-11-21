package com.Steprella.Steprella.services.dtos.responses.users;

import com.Steprella.Steprella.services.dtos.responses.addresses.ListAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.userdetails.ListUserDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListUserResponse {

    private int id;

    private String email;

    private ListUserDetailResponse userDetail;

    private List<ListAddressResponse> addresses;
}
