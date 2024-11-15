package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.services.abstracts.ProductColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product-colors")
@RequiredArgsConstructor
public class ProductColorController {

    private final ProductColorService productColorService;
}
