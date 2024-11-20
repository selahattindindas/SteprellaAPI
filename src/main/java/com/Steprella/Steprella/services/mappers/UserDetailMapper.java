package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.UserDetail;
import com.Steprella.Steprella.services.dtos.requests.userdetails.AddUserDetailRequest;
import com.Steprella.Steprella.services.dtos.requests.userdetails.UpdateUserDetailRequest;
import com.Steprella.Steprella.services.dtos.responses.userdetails.AddUserDetailResponse;
import com.Steprella.Steprella.services.dtos.responses.userdetails.ListUserDetailResponse;
import com.Steprella.Steprella.services.dtos.responses.userdetails.UpdateUserDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDetailMapper {

    UserDetailMapper INSTANCE = Mappers.getMapper(UserDetailMapper.class);

    @Mapping(target = "userId", source = "user.id")
    ListUserDetailResponse listResponseFromUserDetail(UserDetail userDetail);

    @Mapping(target = "user.id", source = "userId")
    UserDetail userDetailFromAddRequest(AddUserDetailRequest request);

    @Mapping(target = "userId", source = "user.id")
    AddUserDetailResponse addResponseFromUserDetail(UserDetail userDetail);

    @Mapping(target = "user.id", source = "userId")
    UserDetail userDetailFromUpdateRequest(UpdateUserDetailRequest request);

    @Mapping(target = "userId", source = "user.id")
    UpdateUserDetailResponse updateResponseFromUserDetail(UserDetail userDetail);
}
