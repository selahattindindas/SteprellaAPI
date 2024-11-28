package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.AuthService;
import com.Steprella.Steprella.services.dtos.requests.users.AddUserRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.users.AddUserResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController extends BaseController{

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<AddUserResponse>> register(@RequestBody @Valid AddUserRequest request) {
        AddUserResponse registerResponse = authService.register(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, registerResponse);
    }
}
