package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.services.dtos.responses.users.ListUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    ListUserResponse listResponseFromUser(User user);
}
