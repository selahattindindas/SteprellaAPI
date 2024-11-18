package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Customer;
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


    ListUserDetailResponse listResponseFromUserDetail(UserDetail userDetail);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", source = "customer")
    UserDetail userDetailFromAddRequest(AddUserDetailRequest request, Customer customer);

    AddUserDetailResponse addResponseFromUserDetail(UserDetail userDetail);

    @Mapping(target = "id", source = "request.id")
    @Mapping(target = "customer", source = "customer")
    UserDetail userDetailFromUpdateRequest(UpdateUserDetailRequest request, Customer customer);

    UpdateUserDetailResponse updateResponseFromUserDetail(UserDetail userDetail);
}
