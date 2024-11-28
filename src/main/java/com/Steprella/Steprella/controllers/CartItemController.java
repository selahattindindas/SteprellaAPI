package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.CartItemService;
import com.Steprella.Steprella.services.dtos.requests.cartitems.AddCartItemRequest;
import com.Steprella.Steprella.services.dtos.requests.cartitems.UpdateCartItemRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.cart_items.AddCartItemResponse;
import com.Steprella.Steprella.services.dtos.responses.cart_items.ListCartItemResponse;
import com.Steprella.Steprella.services.dtos.responses.cart_items.UpdateCartItemResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
@AllArgsConstructor
public class CartItemController extends BaseController {

    private final CartItemService cartItemService;

    @GetMapping("/by-cart-id/{cartId}")
    public ResponseEntity<BaseResponse<List<ListCartItemResponse>>> getItemsByCartId(@PathVariable int cartId){
        List<ListCartItemResponse> cartItems = cartItemService.getItemsByCartId(cartId);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, cartItems);
    }

    @PostMapping("/create-cart-item")
    public ResponseEntity<BaseResponse<AddCartItemResponse>> add(@RequestBody @Valid AddCartItemRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);

        AddCartItemResponse cartItem = cartItemService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, cartItem);
    }

    @PutMapping("/update-cart-item")
    public ResponseEntity<BaseResponse<UpdateCartItemResponse>> update(@RequestBody @Valid UpdateCartItemRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);

        UpdateCartItemResponse cartItem = cartItemService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, cartItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        cartItemService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
