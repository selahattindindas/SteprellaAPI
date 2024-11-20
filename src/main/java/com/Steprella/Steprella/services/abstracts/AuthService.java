package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.users.AddUserRequest;
import com.Steprella.Steprella.services.dtos.responses.users.AddUserResponse;

public interface AuthService {

    AddUserResponse register(AddUserRequest request);
}
