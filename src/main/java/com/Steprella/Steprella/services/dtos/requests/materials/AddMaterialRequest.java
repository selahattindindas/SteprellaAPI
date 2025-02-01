package com.Steprella.Steprella.services.dtos.requests.materials;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddMaterialRequest {

    @NotBlank
    private String name;
}
