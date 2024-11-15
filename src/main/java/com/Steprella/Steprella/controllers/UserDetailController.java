package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.services.abstracts.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-details")
@RequiredArgsConstructor
public class UserDetailController {

    private final UserDetailService userDetailService;
}
