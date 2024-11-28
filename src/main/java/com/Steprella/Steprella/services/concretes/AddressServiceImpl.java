package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.EntityValidator;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Address;
import com.Steprella.Steprella.repositories.AddressRepository;
import com.Steprella.Steprella.services.abstracts.AddressService;
import com.Steprella.Steprella.services.abstracts.CityService;
import com.Steprella.Steprella.services.abstracts.DistrictService;
import com.Steprella.Steprella.services.abstracts.UserService;
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
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;
    private final DistrictService districtService;
    private final CityService cityService;
    private final UserService userService;
    private final EntityValidator entityValidator;

    @Override
    public List<ListAddressResponse> getAddressesByUserId(int userId) {
        userService.getResponseById(userId);
        List<Address> addresses = addressRepository.findAddressByUserId(userId);

        return addresses.stream().map(AddressMapper.INSTANCE::listResponseFromAddress).collect(Collectors.toList());
    }

    @Override
    public ListAddressResponse getById(int id) {
        Address address = findAddressById(id);
        return AddressMapper.INSTANCE.listResponseFromAddress(address);
    }

    @Override
    public AddAddressResponse add(AddAddressRequest request) {
        validateAddressRequest(request.getCityId(), request.getDistrictId(), request.getUserId());
        entityValidator.validateCityDistrictRelation(request.getDistrictId(), request.getCityId());

        Address addAddress = AddressMapper.INSTANCE.addressFromAddRequest(request);
        Address savedAddress = addressRepository.save(addAddress);

        return AddressMapper.INSTANCE.addResponseFromAddress(savedAddress);
    }

    @Override
    public UpdateAddressResponse update(UpdateAddressRequest request) {
        findAddressById(request.getId());
        validateAddressRequest(request.getCityId(), request.getDistrictId(), request.getUserId());
        entityValidator.validateCityDistrictRelation(request.getDistrictId(), request.getCityId());

        Address updateAddress = AddressMapper.INSTANCE.addressFromUpdateRequest(request);
        Address saveAddress = addressRepository.save(updateAddress);

        return AddressMapper.INSTANCE.updateResponseFromAddress(saveAddress);
    }

    @Override
    public void delete(int id) {
        Address address = findAddressById(id);
        addressRepository.delete(address);
    }

    private Address findAddressById(int id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_ADDRESS_NOT_FOUND));
    }

    private void validateAddressRequest(int cityId, int districtId, int userId) {
        cityService.getById(cityId);
        districtService.getById(districtId);
        userService.getResponseById(userId);
    }
}
