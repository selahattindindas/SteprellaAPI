package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.OrderService;
import com.Steprella.Steprella.services.dtos.requests.orders.AddOrderRequest;
import com.Steprella.Steprella.services.dtos.requests.orders.UpdateOrderRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.orders.AddOrderResponse;
import com.Steprella.Steprella.services.dtos.responses.orders.ListOrderResponse;
import com.Steprella.Steprella.services.dtos.responses.orders.UpdateOrderResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController extends BaseController{

    private OrderService orderService;

    @GetMapping("/by-user-id/{userId}")
    public ResponseEntity<BaseResponse<ListOrderResponse>> getByUserId(@PathVariable int userId){
        ListOrderResponse order = orderService.getByUserId(userId);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, order);
    }

    @PostMapping("/create-order")
    public ResponseEntity<BaseResponse<AddOrderResponse>> add(@RequestBody @Valid AddOrderRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        AddOrderResponse addOrder = orderService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, addOrder);
    }

    @PutMapping("/update-order")
    public ResponseEntity<BaseResponse<UpdateOrderResponse>> update(@RequestBody @Valid UpdateOrderRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        UpdateOrderResponse updatedOrder = orderService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        orderService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
