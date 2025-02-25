package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.tokens.RefreshTokenRequest;
import com.Steprella.Steprella.services.dtos.requests.users.AddUserRequest;
import com.Steprella.Steprella.services.dtos.requests.users.LoginUserRequest;
import com.Steprella.Steprella.services.dtos.requests.users.UpdateUserRequest;
import com.Steprella.Steprella.services.dtos.responses.tokens.RefreshTokenResponse;
import com.Steprella.Steprella.services.dtos.responses.users.AddUserResponse;
import com.Steprella.Steprella.services.dtos.responses.users.LoginUserResponse;
import com.Steprella.Steprella.services.dtos.responses.users.UpdateUserResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    AddUserResponse register(AddUserRequest request);

    UpdateUserResponse update(UpdateUserRequest request);

    LoginUserResponse login(LoginUserRequest request, HttpServletResponse response);

    LoginUserResponse adminLogin(LoginUserRequest request, HttpServletResponse response);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request, HttpServletResponse response);
}
