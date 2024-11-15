package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.userdetails.AddUserDetailRequest;
import com.Steprella.Steprella.services.dtos.requests.userdetails.UpdateUserDetailRequest;
import com.Steprella.Steprella.services.dtos.responses.userdetails.AddUserDetailResponse;
import com.Steprella.Steprella.services.dtos.responses.userdetails.ListUserDetailResponse;
import com.Steprella.Steprella.services.dtos.responses.userdetails.UpdateUserDetailResponse;

public interface UserDetailService {

    ListUserDetailResponse getUserDetailByUserId(int userId);

    AddUserDetailResponse add(AddUserDetailRequest request);

    UpdateUserDetailResponse update(UpdateUserDetailRequest request);
}
