package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
public class FavoriteController extends BaseController {

    private final FavoriteService favoriteService;

    @GetMapping("/get-favorites")
    public ResponseEntity<BaseResponse<List<ListFavoriteResponse>>> getFavorites(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<ListFavoriteResponse> favorites = favoriteService.getFavorites(page, size);
        if (favorites == null || favorites.isEmpty()) {
            return sendResponse(HttpStatus.OK, "Favori ürün bulunamadı.", Collections.emptyList(), 0);
        }
        int totalCount = favoriteService.getTotalCount();
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, favorites, totalCount);
    }

    @PostMapping("/create-favorite")
    public ResponseEntity<BaseResponse<AddFavoriteResponse>> add(@RequestBody @Valid AddFavoriteRequest request) {
        AddFavoriteResponse favorite = favoriteService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, favorite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable int id) {
        favoriteService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
