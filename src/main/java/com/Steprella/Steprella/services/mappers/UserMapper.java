package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.services.dtos.responses.addresses.ListAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.users.ListUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userDetail", source = "user.userDetail")
    @Mapping(target = "addresses", source = "addresses")
    @Mapping(target = "email", source = "user.email")
    ListUserResponse listResponseFromUser(User user,
                                          List<ListAddressResponse> addresses);
}
