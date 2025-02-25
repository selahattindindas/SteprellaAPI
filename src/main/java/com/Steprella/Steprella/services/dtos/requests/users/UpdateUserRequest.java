package com.Steprella.Steprella.services.dtos.requests.users;

import com.Steprella.Steprella.services.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    @Pattern(regexp = "^[1-9][0-9]{9}$")
    private String phone;

    @NotNull
    private Gender gender;
}