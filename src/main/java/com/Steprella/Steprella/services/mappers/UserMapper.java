package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Address;
import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.services.dtos.responses.addresses.ListAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.users.ListUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = AddressMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "email", source = "user.email")
    ListUserResponse listResponseFromUser(User user);

    default List<ListAddressResponse> mapAddressesToDTO(List<Address> addresses) {
        return addresses.stream()
                .map(AddressMapper.INSTANCE::listResponseFromAddress)
                .collect(Collectors.toList());
    }
}
