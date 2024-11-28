package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.FavoriteService;
import com.Steprella.Steprella.services.dtos.requests.favorites.AddFavoriteRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.favorites.AddFavoriteResponse;
import com.Steprella.Steprella.services.dtos.responses.favorites.ListFavoriteResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@AllArgsConstructor
public class FavoriteController extends BaseController{

    private final FavoriteService favoriteService;

    @GetMapping("/by-user-id/{userId}")
    public ResponseEntity<BaseResponse<List<ListFavoriteResponse>>> getFavoritesByUserId(@PathVariable int userId){
        List<ListFavoriteResponse> favorites = favoriteService.getFavoritesByUserId(userId);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, favorites);
    }

    @PostMapping("/create-favorite")
    public ResponseEntity<BaseResponse<AddFavoriteResponse>> add(@RequestBody @Valid AddFavoriteRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);

        AddFavoriteResponse favorite = favoriteService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, favorite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        favoriteService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
