package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.services.abstracts.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController extends BaseController {

    private final AddressService addressService;
}
