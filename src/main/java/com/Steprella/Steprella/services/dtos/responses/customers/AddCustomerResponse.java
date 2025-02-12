package com.Steprella.Steprella.services.dtos.responses.customers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddCustomerResponse {

    private int id;

    private String fullName;

    private String email;
}
