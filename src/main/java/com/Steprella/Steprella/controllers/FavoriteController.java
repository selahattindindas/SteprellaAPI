package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.FavoriteService;
import com.Steprella.Steprella.services.abstracts.ProductVariantService;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.dtos.requests.favorites.AddFavoriteRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.favorites.AddFavoriteResponse;
import com.Steprella.Steprella.services.dtos.responses.favorites.ListFavoriteResponse;
import com.Steprella.Steprella.services.dtos.responses.users.ListUserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController extends BaseController{

    private final FavoriteService favoriteService;
    private final UserService userService;
    private final ProductVariantService productVariantService;

    @GetMapping("/by-user-id/{userId}")
    public ResponseEntity<BaseResponse<List<ListFavoriteResponse>>> getFavoritesByUserId(@PathVariable int userId){
        ListUserResponse user = userService.getResponseById(userId);
        if (user == null) {
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_USER_NOT_FOUND, null);
        }

        List<ListFavoriteResponse> favorites = favoriteService.getFavoritesByUserId(userId);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, favorites);
    }

    @PostMapping("/create-favorite")
    public ResponseEntity<BaseResponse<AddFavoriteResponse>> add(@RequestBody @Valid AddFavoriteRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);

        if (userService.getById(request.getUserId()) == null)
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_USER_NOT_FOUND, null);

        if (productVariantService.getById(request.getProductVariantId()) == null)
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_PRODUCT_NOT_FOUND, null);

        AddFavoriteResponse favorite = favoriteService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, favorite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        favoriteService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
