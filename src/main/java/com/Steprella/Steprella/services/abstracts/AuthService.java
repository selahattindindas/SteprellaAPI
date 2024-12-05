package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.services.dtos.requests.users.AddUserRequest;
import com.Steprella.Steprella.services.dtos.requests.users.LoginUserRequest;
import com.Steprella.Steprella.services.dtos.responses.users.AddUserResponse;

public interface AuthService {

    AddUserResponse register(AddUserRequest request);

    User login(LoginUserRequest request);
}
