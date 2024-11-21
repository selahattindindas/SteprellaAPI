package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.users.ListUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController extends BaseController{

    private final UserService userService;

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<ListUserResponse>>> getAll(){
        List<ListUserResponse> users = userService.getAll();
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListUserResponse>> getById(@PathVariable int id) {
        ListUserResponse user = userService.getResponseById(id);
        if (user == null){
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_USER_NOT_FOUND, null);
        }
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, user);
    }
}
