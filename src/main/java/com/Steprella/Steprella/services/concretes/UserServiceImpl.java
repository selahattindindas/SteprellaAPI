package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.dtos.requests.users.AddUserRequest;
import com.Steprella.Steprella.services.dtos.responses.users.AddUserResponse;
import com.Steprella.Steprella.services.dtos.responses.users.ListUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public List<ListUserResponse> getAll() {
        return List.of();
    }

    @Override
    public ListUserResponse getById(int id) {
        return null;
    }

    @Override
    public AddUserResponse add(AddUserRequest request) {
        return null;
    }
}
