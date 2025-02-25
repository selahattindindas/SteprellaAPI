package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.services.dtos.requests.users.AddUserRequest;
import com.Steprella.Steprella.services.dtos.requests.users.UpdateUserRequest;
import com.Steprella.Steprella.services.dtos.responses.users.AddUserResponse;
import com.Steprella.Steprella.services.dtos.responses.users.UpdateUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthMapper {

    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    @Mapping(target = "id", ignore = true)
    User userFromAddRequest(AddUserRequest request);

    AddUserResponse addResponseFromUser(User user);

    @Mapping(target = "customer", source = "customer")
    User userFromAddRequest(UpdateUserRequest request, Customer customer);

    UpdateUserResponse updateResponseFromUser(User user);
}
