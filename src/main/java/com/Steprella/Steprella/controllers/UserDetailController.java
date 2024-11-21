package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.UserDetailService;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.dtos.requests.userdetails.AddUserDetailRequest;
import com.Steprella.Steprella.services.dtos.requests.userdetails.UpdateUserDetailRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.userdetails.AddUserDetailResponse;
import com.Steprella.Steprella.services.dtos.responses.userdetails.ListUserDetailResponse;
import com.Steprella.Steprella.services.dtos.responses.userdetails.UpdateUserDetailResponse;
import com.Steprella.Steprella.services.dtos.responses.users.ListUserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-details")
@RequiredArgsConstructor
public class UserDetailController extends BaseController{

    private final UserDetailService userDetailService;
    private final UserService userService;

    @GetMapping("/by-user-id/{userId}")
    public ResponseEntity<BaseResponse<ListUserDetailResponse>> getUserDetailByUserId(@PathVariable int userId){
        ListUserResponse userResponse = userService.getResponseById(userId);
        if (userResponse == null) {
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_USER_NOT_FOUND, null);
        }

        ListUserDetailResponse userDetail = userDetailService.getUserDetailByUserId(userId);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, userDetail);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListUserDetailResponse>> getById(@PathVariable int id){
        ListUserDetailResponse userDetail = userDetailService.getUserDetailByUserId(id);
        if(userDetail == null)
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_USER_DETAIL_NOT_FOUND, null);
        else
            return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, userDetail);
    }

    @PostMapping("/create-user-detail")
    public ResponseEntity<BaseResponse<AddUserDetailResponse>> add(@RequestBody @Valid AddUserDetailRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }
        if (userService.getById(request.getUserId()) == null) {
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_USER_NOT_FOUND, null);
        }

        AddUserDetailResponse addProductSize = userDetailService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, addProductSize);
    }

    @PutMapping("/update-user-detail")
    public ResponseEntity<BaseResponse<UpdateUserDetailResponse>> update(@RequestBody @Valid UpdateUserDetailRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        if (userService.getResponseById(request.getUserId()) == null) {
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_USER_NOT_FOUND, null);
        }

        UpdateUserDetailResponse updateUserDetail = userDetailService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, updateUserDetail);
    }
}
