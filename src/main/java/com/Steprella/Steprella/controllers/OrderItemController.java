package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.services.abstracts.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order-items")
@AllArgsConstructor
public class OrderItemController extends BaseController{

    private OrderItemService orderItemService;


}
