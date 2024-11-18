package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.services.dtos.requests.users.AddUserRequest;
import com.Steprella.Steprella.services.dtos.responses.users.AddUserResponse;
import com.Steprella.Steprella.services.dtos.responses.users.ListUserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<ListUserResponse> getAll();

    ListUserResponse getResponseById(int id);

    User getById(int id);

    User getLoggedInUser();

    AddUserResponse add(AddUserRequest request);
}
