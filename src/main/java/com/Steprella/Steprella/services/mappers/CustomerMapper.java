package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.services.dtos.requests.customers.AddCustomerRequest;
import com.Steprella.Steprella.services.dtos.responses.customers.AddCustomerResponse;
import com.Steprella.Steprella.services.dtos.responses.customers.ListCustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    ListCustomerResponse listResponseFromCustomer(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "favorites", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "userDetail", ignore = true)
    Customer customerFromAddRequest(AddCustomerRequest request);

    AddCustomerResponse addResponseFromCustomer(Customer customer);
}
