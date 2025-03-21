package com.Steprella.Steprella.services.dtos.requests.addresses;

import jakarta.validation.constraints.Min;
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
public class AddAddressRequest {

    @NotBlank
    private String description;

    @NotBlank
    private String title;

    @NotNull
    @Min(1)
    private int cityId;

    @NotNull
    @Min(1)
    private int districtId;
}
