package com.Steprella.Steprella.services.dtos.responses.addresses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddAddressResponse {

    private int id;

    private int cityId;

    private int districtId;

    private int userId;
}
