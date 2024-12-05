package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.services.JwtService;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.services.abstracts.AuthService;
import com.Steprella.Steprella.services.dtos.requests.users.AddUserRequest;
import com.Steprella.Steprella.services.dtos.requests.users.LoginUserRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
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
public class AuthController extends BaseController{
    private final JwtService jwtService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<AddUserResponse>> register(@RequestBody @Valid AddUserRequest request) {
        AddUserResponse registerResponse = authService.register(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginUserResponse>> login(@RequestBody @Valid LoginUserRequest request){
        User authenticatedUser = authService.login(request);

        String jwtToken = jwtService.generateToken(authenticatedUser, authenticatedUser.getFullName(), authenticatedUser.getRole(), authenticatedUser.getPhone() );

        LoginUserResponse loginResponse = new LoginUserResponse(jwtToken, jwtService.getExpirationTime());

        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, loginResponse);
    }
}
