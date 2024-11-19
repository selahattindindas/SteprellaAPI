package com.Steprella.Steprella.services.dtos.responses.addresses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListAddressResponse {

    private int id;

    private String cityName;

    private String districtName;

    private int userId;
}
