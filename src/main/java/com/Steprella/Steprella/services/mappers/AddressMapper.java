package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Address;
import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.services.dtos.requests.addresses.AddAddressRequest;
import com.Steprella.Steprella.services.dtos.requests.addresses.UpdateAddressRequest;
import com.Steprella.Steprella.services.dtos.responses.addresses.AddAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.addresses.ListAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.addresses.UpdateAddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(target = "cityName", source = "city.name")
    @Mapping(target = "districtName", source = "district.name")
    ListAddressResponse listResponseFromAddress(Address address);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "city.id", source = "request.cityId")
    @Mapping(target = "district.id", source = "request.districtId")
    @Mapping(target = "customer", source = "customer")
    Address addressFromAddRequest(AddAddressRequest request, Customer customer);

    @Mapping(target = "id", source = "request.id")
    @Mapping(target = "city.id", source = "request.cityId")
    @Mapping(target = "district.id", source = "request.districtId")
    @Mapping(target = "customer", source = "customer")
    Address addressFromUpdateRequest(UpdateAddressRequest request, Customer customer);

    @Mapping(target = "cityId", source = "city.id")
    @Mapping(target = "districtId", source = "district.id")
    AddAddressResponse addResponseFromAddress(Address address);

    @Mapping(target = "cityId", source = "city.id")
    @Mapping(target = "districtId", source = "district.id")
    UpdateAddressResponse updateResponseFromAddress(Address address);

    default List<ListAddressResponse> listResponseFromAddresses(List<Address> addresses) {
        if (addresses == null) {
            return null;
        }
        return addresses.stream()
                .map(this::listResponseFromAddress)
                .collect(Collectors.toList());
    }
}