package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.services.abstracts.ProductSizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product-sizes")
@RequiredArgsConstructor
public class ProductSizeController {

    private final ProductSizeService productSizeService;
}
