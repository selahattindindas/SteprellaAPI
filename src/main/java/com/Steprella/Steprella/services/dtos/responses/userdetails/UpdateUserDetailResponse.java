package com.Steprella.Steprella.services.dtos.responses.userdetails;

import com.Steprella.Steprella.services.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDetailResponse {

    private int userId;

    private String firstName;

    private String lastName;

    private String phone;

    private Gender gender;
}
