package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.services.dtos.responses.users.ListUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "gender", source = "gender")
    ListUserResponse listResponseFromUser(User user);
}
