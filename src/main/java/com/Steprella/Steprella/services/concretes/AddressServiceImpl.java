package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.Address;
import com.Steprella.Steprella.repositories.AddressRepository;
import com.Steprella.Steprella.services.abstracts.AddressService;
import com.Steprella.Steprella.services.abstracts.DistrictService;
import com.Steprella.Steprella.services.dtos.requests.addresses.AddAddressRequest;
import com.Steprella.Steprella.services.dtos.requests.addresses.UpdateAddressRequest;
import com.Steprella.Steprella.services.dtos.responses.addresses.AddAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.addresses.ListAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.addresses.UpdateAddressResponse;
import com.Steprella.Steprella.services.mappers.AddressMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final DistrictService districtService;

    @Override
    public List<ListAddressResponse> getAddressesByUserId(int userId) {
        List<Address> addresses = addressRepository.findAddressByUserId(userId);

        return addresses.stream().map(AddressMapper.INSTANCE::listResponseFromAddress).collect(Collectors.toList());
    }

    @Override
    public ListAddressResponse getById(int id) {
        Address address = addressRepository.findById(id).orElse(null);

        return AddressMapper.INSTANCE.listResponseFromAddress(address);
    }

    @Override
    public AddAddressResponse add(AddAddressRequest request) {
        Address addAddress = AddressMapper.INSTANCE.addressFromAddRequest(request);
        Address saveAddress = addressRepository.save(addAddress);

        return AddressMapper.INSTANCE.addResponseFromAddress(saveAddress);
    }

    @Override
    public UpdateAddressResponse update(UpdateAddressRequest request) {
        Address updateAddress = AddressMapper.INSTANCE.addressFromUpdateRequest(request);
        Address saveAddress = addressRepository.save(updateAddress);

        return AddressMapper.INSTANCE.updateResponseFromAddress(saveAddress);
    }

    @Override
    public void delete(int id) {
        Address address = addressRepository.findById(id).orElse(null);
        assert address != null;
        addressRepository.delete(address);
    }

    @Override
    public boolean isDistrictBelongsToCity(int districtId, int cityId) {
        return districtService.getById(districtId).getCityId() != cityId;
    }
}
