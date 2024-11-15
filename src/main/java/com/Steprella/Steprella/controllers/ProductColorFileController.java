package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.services.abstracts.ProductColorFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product-color-files")
@RequiredArgsConstructor
public class ProductColorFileController {

    private final ProductColorFileService productColorFileService;
}
