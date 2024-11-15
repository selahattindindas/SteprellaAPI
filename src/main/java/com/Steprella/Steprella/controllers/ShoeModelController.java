package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.services.abstracts.ShoeModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shoe-models")
@RequiredArgsConstructor
public class ShoeModelController {

    private final ShoeModelService shoeModelService;
}
