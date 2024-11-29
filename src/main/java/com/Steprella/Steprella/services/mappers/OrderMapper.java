package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Address;
import com.Steprella.Steprella.entities.concretes.Order;
import com.Steprella.Steprella.services.dtos.requests.orders.AddOrderRequest;
import com.Steprella.Steprella.services.dtos.requests.orders.UpdateOrderRequest;
import com.Steprella.Steprella.services.dtos.responses.addresses.ListAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.orders.AddOrderResponse;
import com.Steprella.Steprella.services.dtos.responses.orders.ListOrderResponse;
import com.Steprella.Steprella.services.dtos.responses.orders.UpdateOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = AddressMapper.class)
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "items", source = "items")
    @Mapping(target = "shippingAddress", expression = "java(mapAddress(order.getShippingAddress()))")
    ListOrderResponse listResponseFromOrder(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "shippingAddress.id", source = "shippingAddressId")
    Order orderFromAddRequest(AddOrderRequest request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "shippingAddressId", source = "shippingAddress.id")
    AddOrderResponse addResponseFromOrder(Order order);

    Order orderFromUpdateRequest(UpdateOrderRequest request);

    UpdateOrderResponse updateResponseFromOrder(Order order);

    default ListAddressResponse mapAddress(Address address) {
        return AddressMapper.INSTANCE.listResponseFromAddress(address);
    }
}
