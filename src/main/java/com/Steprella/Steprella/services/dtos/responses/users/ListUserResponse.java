package com.Steprella.Steprella.services.dtos.responses.users;

import com.Steprella.Steprella.services.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListUserResponse {

    private int id;

    private String email;

    private String fullName;

    private String phone;

    private Gender gender;
}
