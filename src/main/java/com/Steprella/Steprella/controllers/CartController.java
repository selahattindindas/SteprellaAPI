package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.CartService;
import com.Steprella.Steprella.services.dtos.requests.carts.AddCartRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.carts.AddCartResponse;
import com.Steprella.Steprella.services.dtos.responses.carts.ListCartResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@AllArgsConstructor
public class CartController extends BaseController{

    private final CartService cartService;

    @GetMapping("/by-user-id/{userId}")
    @PreAuthorize("hasRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<BaseResponse<ListCartResponse>> getCartByUserId(@PathVariable int userId){
        ListCartResponse cart = cartService.getCartByUserId(userId);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, cart);
    }

    @PostMapping("/create-cart")
    @PreAuthorize("hasRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<BaseResponse<AddCartResponse>> add(@RequestBody @Valid AddCartRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);

        AddCartResponse cart = cartService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, cart);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        cartService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }

}
