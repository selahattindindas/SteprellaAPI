package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.services.abstracts.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController extends BaseController{

    private final BrandService brandService;
}
