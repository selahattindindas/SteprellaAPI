package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.entities.concretes.Address;
import com.Steprella.Steprella.services.dtos.requests.addresses.AddAddressRequest;
import com.Steprella.Steprella.services.dtos.requests.addresses.UpdateAddressRequest;
import com.Steprella.Steprella.services.dtos.responses.addresses.AddAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.addresses.ListAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.addresses.UpdateAddressResponse;

import java.util.List;

public interface AddressService {

    List<ListAddressResponse> getAddresses();

    ListAddressResponse getById(int id);

    Address getAddressById(int id);

    AddAddressResponse add(AddAddressRequest request);

    UpdateAddressResponse update(UpdateAddressRequest request);

    void delete(int id);
}
