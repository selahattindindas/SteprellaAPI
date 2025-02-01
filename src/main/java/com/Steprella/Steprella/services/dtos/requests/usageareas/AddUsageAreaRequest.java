package com.Steprella.Steprella.services.dtos.requests.usageareas;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddUsageAreaRequest {

    @NotBlank
    private String name;
}
