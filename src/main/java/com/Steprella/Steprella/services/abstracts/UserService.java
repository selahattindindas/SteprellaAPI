package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.users.AddUserRequest;
import com.Steprella.Steprella.services.dtos.responses.users.AddUserResponse;
import com.Steprella.Steprella.services.dtos.responses.users.ListUserResponse;

import java.util.List;

public interface UserService {

    List<ListUserResponse> getAll();

    ListUserResponse getById(int id);

    AddUserResponse add(AddUserRequest request);
}
