package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.CartService;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.carts.ListCartResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
public class CartController extends BaseController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<BaseResponse<ListCartResponse>> getCart() {
        ListCartResponse cart = cartService.getCart();
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, cart);
    }
}
