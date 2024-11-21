package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.entities.concretes.UserDetail;
import com.Steprella.Steprella.repositories.UserDetailRepository;
import com.Steprella.Steprella.services.abstracts.UserDetailService;
import com.Steprella.Steprella.services.abstracts.UserService;
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
    private UserService userService;

    @Override
    public ListUserDetailResponse getUserDetailByUserId(int userId) {
        UserDetail userDetail = userDetailRepository.findByUserId(userId);
        return UserDetailMapper.INSTANCE.listResponseFromUserDetail(userDetail);
    }

    @Override
    public AddUserDetailResponse add(AddUserDetailRequest request) {
        User user = userService.getById(request.getUserId());
        UserDetail addDetail = UserDetailMapper.INSTANCE.userDetailFromAddRequest(request);
        addDetail.setUser(user);
        UserDetail saveDetail = userDetailRepository.save(addDetail);

        return UserDetailMapper.INSTANCE.addResponseFromUserDetail(saveDetail);
    }

    @Override
    public UpdateUserDetailResponse update(UpdateUserDetailRequest request) {
        User user = userService.getById(request.getUserId());
        UserDetail updateDetail = UserDetailMapper.INSTANCE.userDetailFromUpdateRequest(request);
        updateDetail.setUser(user);
        UserDetail saveDetail = userDetailRepository.save(updateDetail);

        return UserDetailMapper.INSTANCE.updateResponseFromUserDetail(saveDetail);
    }
}
