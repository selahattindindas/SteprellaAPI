package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.tokens.RefreshTokenRequest;
import com.Steprella.Steprella.services.dtos.requests.users.AddUserRequest;
import com.Steprella.Steprella.services.dtos.requests.users.LoginUserRequest;
import com.Steprella.Steprella.services.dtos.responses.tokens.RefreshTokenResponse;
import com.Steprella.Steprella.services.dtos.responses.users.AddUserResponse;
import com.Steprella.Steprella.services.dtos.responses.users.LoginUserResponse;

public interface AuthService {

    AddUserResponse register(AddUserRequest request);

    LoginUserResponse login(LoginUserRequest request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}
