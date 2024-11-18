package com.Steprella.Steprella.services.dtos.requests.addresses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAddressRequest {

    @NotNull
    private int id;

    @NotBlank
    private String description;

    @NotNull
    private int cityId;

    @NotNull
    private int districtId;
}
