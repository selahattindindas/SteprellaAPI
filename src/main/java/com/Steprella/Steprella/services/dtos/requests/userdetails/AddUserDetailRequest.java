package com.Steprella.Steprella.services.dtos.requests.userdetails;

import com.Steprella.Steprella.services.enums.Gender;
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
public class AddUserDetailRequest {

    @NotBlank
    private String firstName;

    @NotNull
    private int userId;

    @NotBlank
    private String lastName;

    @NotBlank
    private String phone;

    @NotNull
    private Gender gender;

}
