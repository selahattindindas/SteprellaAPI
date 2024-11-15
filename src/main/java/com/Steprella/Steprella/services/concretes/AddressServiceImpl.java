package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.services.abstracts.AddressService;
import com.Steprella.Steprella.services.dtos.requests.addresses.AddAddressRequest;
import com.Steprella.Steprella.services.dtos.requests.addresses.UpdateAddressRequest;
import com.Steprella.Steprella.services.dtos.responses.addresses.AddAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.addresses.ListAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.addresses.UpdateAddressResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    @Override
    public List<ListAddressResponse> getAddressesByUserId(int userId) {
        return List.of();
    }

    @Override
    public ListAddressResponse getById(int id) {
        return null;
    }

    @Override
    public AddAddressResponse add(AddAddressRequest request) {
        return null;
    }

    @Override
    public UpdateAddressResponse update(UpdateAddressRequest request) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
