package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.addresses.AddAddressRequest;
import com.Steprella.Steprella.services.dtos.requests.addresses.UpdateAddressRequest;
import com.Steprella.Steprella.services.dtos.responses.addresses.AddAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.addresses.ListAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.addresses.UpdateAddressResponse;

import java.util.List;

public interface AddressService {

    List<ListAddressResponse> getAddressesByUserId(int userId);

    ListAddressResponse getById(int id);

    AddAddressResponse add(AddAddressRequest request);

    UpdateAddressResponse update(UpdateAddressRequest request);

    void delete(int id);

    boolean isDistrictBelongsToCity(int districtId, int cityId);
}
