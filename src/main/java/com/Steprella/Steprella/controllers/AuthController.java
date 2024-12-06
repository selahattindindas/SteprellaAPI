package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.services.JwtService;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.services.abstracts.AuthService;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.dtos.requests.tokens.RefreshTokenRequest;
import com.Steprella.Steprella.services.dtos.requests.users.AddUserRequest;
import com.Steprella.Steprella.services.dtos.requests.users.LoginUserRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.tokens.RefreshTokenResponse;
import com.Steprella.Steprella.services.dtos.responses.users.AddUserResponse;
import com.Steprella.Steprella.services.dtos.responses.users.LoginUserResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController extends BaseController {
    private final JwtService jwtService;
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<AddUserResponse>> register(@RequestBody @Valid AddUserRequest request) {
        AddUserResponse registerResponse = authService.register(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginUserResponse>> login(@RequestBody @Valid LoginUserRequest request) {
        User authenticatedUser = authService.login(request);

        String accessToken = jwtService.generateAccessToken(authenticatedUser, authenticatedUser.getFullName(), authenticatedUser.getRole(), authenticatedUser.getPhone());
        String refreshToken = jwtService.generateRefreshToken(authenticatedUser, authenticatedUser.getFullName(), authenticatedUser.getRole(), authenticatedUser.getPhone());
        long accessTokenExpiration = jwtService.getExpirationTime();

        LoginUserResponse loginResponse = new LoginUserResponse(accessToken, refreshToken, accessTokenExpiration);

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, loginResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<BaseResponse<RefreshTokenResponse>> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (jwtService.isTokenExpired(refreshToken)) {
            return sendResponse(HttpStatus.UNAUTHORIZED, Messages.Error.TOKEN_EXPIRED, null);
        }

        String username = jwtService.extractUsername(refreshToken);

        User user = userService.getByEmail(username);

        String newAccessToken = jwtService.generateAccessToken(user, user.getFullName(), user.getRole(), user.getPhone());
        String newRefreshToken = jwtService.generateRefreshToken(user, user.getFullName(), user.getRole(), user.getPhone());

        RefreshTokenResponse response = new RefreshTokenResponse(newAccessToken, newRefreshToken, jwtService.getExpirationTime());

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, response);
    }
}
