package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.UserDetail;
import com.Steprella.Steprella.repositories.UserDetailRepository;
import com.Steprella.Steprella.services.abstracts.UserDetailService;
import com.Steprella.Steprella.services.dtos.requests.userdetails.AddUserDetailRequest;
import com.Steprella.Steprella.services.dtos.requests.userdetails.UpdateUserDetailRequest;
import com.Steprella.Steprella.services.dtos.responses.userdetails.AddUserDetailResponse;
import com.Steprella.Steprella.services.dtos.responses.userdetails.ListUserDetailResponse;
import com.Steprella.Steprella.services.dtos.responses.userdetails.UpdateUserDetailResponse;
import com.Steprella.Steprella.services.mappers.UserDetailMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {

    private UserDetailRepository userDetailRepository;

    @Override
    public ListUserDetailResponse getUserDetailByUserId(int userId) {
        UserDetail userDetail = userDetailRepository.findByUserId(userId);
        return UserDetailMapper.INSTANCE.listResponseFromUserDetail(userDetail);
    }

    @Override
    public AddUserDetailResponse add(AddUserDetailRequest request) {
        UserDetail addDetail = UserDetailMapper.INSTANCE.userDetailFromAddRequest(request);
        UserDetail saveDetail = userDetailRepository.save(addDetail);

        return UserDetailMapper.INSTANCE.addResponseFromUserDetail(saveDetail);
    }

    @Override
    public UpdateUserDetailResponse update(UpdateUserDetailRequest request) {
        UserDetail updateDetail = UserDetailMapper.INSTANCE.userDetailFromUpdateRequest(request);
        UserDetail saveDetail = userDetailRepository.save(updateDetail);

        return UserDetailMapper.INSTANCE.updateResponseFromUserDetail(saveDetail);
    }
}
