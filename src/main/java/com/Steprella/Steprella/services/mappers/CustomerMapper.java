package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.services.dtos.requests.customers.AddCustomerRequest;
import com.Steprella.Steprella.services.dtos.responses.customers.AddCustomerResponse;
import com.Steprella.Steprella.services.dtos.responses.customers.ListCustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AddressMapper.class, OrderMapper.class, CommentMapper.class,
        FavoriteMapper.class, CartMapper.class})
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "fullName", source = "user.fullName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "addresses", source = "addresses")
    @Mapping(target = "orders", source = "orders")
    @Mapping(target = "comments", source = "comments")
    @Mapping(target = "favorites", source = "favorites")
    @Mapping(target = "cart", source = "cart")
    ListCustomerResponse listResponseFromCustomer(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    Customer customerFromAddRequest(AddCustomerRequest request);

    @Mapping(target = "fullName", source = "user.fullName")
    @Mapping(target = "email", source = "user.email")
    AddCustomerResponse addResponseFromCustomer(Customer customer);
}
