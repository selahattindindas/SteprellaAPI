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
public class AddUserResponse {

    private int id;

    private String email;

    private String password;

    private String fullName;

    private String phone;

    private Gender gender;

    private boolean isVerified;
}
