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
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
public class OrderController extends BaseController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<ListOrderResponse>>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<ListOrderResponse> orders = orderService.getOrders(page, size);
        int totalCount = orderService.getTotalCount();
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, orders, totalCount);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<AddOrderResponse>> add(@RequestBody @Valid AddOrderRequest request) {
        AddOrderResponse addOrder = orderService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, addOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable int id) {
        orderService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
