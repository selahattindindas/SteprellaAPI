package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Address;
import com.Steprella.Steprella.services.dtos.requests.addresses.AddAddressRequest;
import com.Steprella.Steprella.services.dtos.requests.addresses.UpdateAddressRequest;
import com.Steprella.Steprella.services.dtos.responses.addresses.AddAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.addresses.ListAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.addresses.UpdateAddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(target = "cityName", source = "city.name")
    @Mapping(target = "districtName", source = "district.name")
    ListAddressResponse listResponseFromAddress(Address address);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city.id", source = "cityId")
    @Mapping(target = "district.id", source = "districtId")
    @Mapping(target = "user.id", source = "userId")
    Address addressFromAddRequest(AddAddressRequest request);

    @Mapping(target = "cityId", source = "city.id")
    @Mapping(target = "districtId", source = "district.id")
    @Mapping(target = "userId", source = "user.id")
    AddAddressResponse addResponseFromAddress(Address address);

    @Mapping(target = "city.id", source = "cityId")
    @Mapping(target = "district.id", source = "districtId")
    @Mapping(target = "user.id", source = "userId")
    Address addressFromUpdateRequest(UpdateAddressRequest request);

    @Mapping(target = "cityId", source = "city.id")
    @Mapping(target = "districtId", source = "district.id")
    @Mapping(target = "userId", source = "user.id")
    UpdateAddressResponse updateResponseFromAddress(Address address);
}
