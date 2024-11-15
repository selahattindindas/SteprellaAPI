package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.services.abstracts.UserDetailService;
import com.Steprella.Steprella.services.dtos.requests.userdetails.AddUserDetailRequest;
import com.Steprella.Steprella.services.dtos.requests.userdetails.UpdateUserDetailRequest;
import com.Steprella.Steprella.services.dtos.responses.userdetails.AddUserDetailResponse;
import com.Steprella.Steprella.services.dtos.responses.userdetails.ListUserDetailResponse;
import com.Steprella.Steprella.services.dtos.responses.userdetails.UpdateUserDetailResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {

    @Override
    public ListUserDetailResponse getUserDetailByUserId(int userId) {
        return null;
    }

    @Override
    public AddUserDetailResponse add(AddUserDetailRequest request) {
        return null;
    }

    @Override
    public UpdateUserDetailResponse update(UpdateUserDetailRequest request) {
        return null;
    }
}
