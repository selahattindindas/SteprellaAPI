package com.Steprella.Steprella.services.dtos.requests.shoemodels;

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
public class UpdateShoeModelRequest {

    @NotNull
    private int id;

    @NotBlank
    private String name;

    @NotNull
    private int brandId;
}
