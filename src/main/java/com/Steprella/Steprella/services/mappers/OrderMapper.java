package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Address;
import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.entities.concretes.Order;
import com.Steprella.Steprella.entities.concretes.OrderItem;
import com.Steprella.Steprella.services.dtos.requests.orders.AddOrderRequest;
import com.Steprella.Steprella.services.dtos.requests.orders.UpdateOrderRequest;
import com.Steprella.Steprella.services.dtos.responses.addresses.ListAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.orderitems.ListOrderItemResponse;
import com.Steprella.Steprella.services.dtos.responses.orders.AddOrderResponse;
import com.Steprella.Steprella.services.dtos.responses.orders.ListOrderResponse;
import com.Steprella.Steprella.services.dtos.responses.orders.UpdateOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {AddressMapper.class, CustomerMapper.class})
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "items", expression = "java(mapOrderItems(order.getItems()))")
    @Mapping(target = "shippingAddress", expression = "java(mapAddress(order.getShippingAddress()))")
    ListOrderResponse listResponseFromOrder(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "shippingAddress.id", source = "request.shippingAddressId")
    Order orderFromAddRequest(AddOrderRequest request, Customer customer);

    @Mapping(target = "shippingAddressId", source = "shippingAddress.id")
    AddOrderResponse addResponseFromOrder(Order order);

    Order orderFromUpdateRequest(UpdateOrderRequest request);

    UpdateOrderResponse updateResponseFromOrder(Order order);

    default ListAddressResponse mapAddress(Address address) {
        return AddressMapper.INSTANCE.listResponseFromAddress(address);
    }

    default List<ListOrderItemResponse> mapOrderItems(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItemMapper.INSTANCE::listResponseFromOrderItem)
                .collect(Collectors.toList());
    }
}
