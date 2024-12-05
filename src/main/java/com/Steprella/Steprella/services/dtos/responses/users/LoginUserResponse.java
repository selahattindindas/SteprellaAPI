package com.Steprella.Steprella.services.dtos.responses.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserResponse {

    private String token;

    private long expiresIn;
}
