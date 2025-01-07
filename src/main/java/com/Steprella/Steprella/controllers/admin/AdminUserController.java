package com.Steprella.Steprella.controllers.admin;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.users.ListUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin-users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminUserController extends BaseController {

    private final UserService userService;

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<ListUserResponse>>> getAll(
            @RequestParam int page,
            @RequestParam int size) {

        List<ListUserResponse> users = userService.getAll(page, size);
        int totalCount = userService.getTotalCount();

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, users, totalCount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListUserResponse>> getById(@PathVariable int id) {
        ListUserResponse user = userService.getResponseById(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, user);
    }
}
