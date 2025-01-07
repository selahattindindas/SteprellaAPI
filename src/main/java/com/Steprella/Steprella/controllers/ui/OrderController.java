package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.OrderService;
import com.Steprella.Steprella.services.dtos.requests.orders.AddOrderRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.orders.AddOrderResponse;
import com.Steprella.Steprella.services.dtos.responses.orders.ListOrderResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController extends BaseController {

    private OrderService orderService;

    @GetMapping("/by-user-id/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<BaseResponse<List<ListOrderResponse>>> getByUserId(
            @PathVariable int userId,
            @RequestParam int page,
            @RequestParam int size) {

        List<ListOrderResponse> orders = orderService.getByUserId(userId, page, size);
        int totalCount = orderService.getTotalCount();

        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, orders, totalCount);
    }

    @PostMapping("/create-order")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<BaseResponse<AddOrderResponse>> add(@RequestBody @Valid AddOrderRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        AddOrderResponse addOrder = orderService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, addOrder);
    }
}
