package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.EntityValidator;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Address;
import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.repositories.AddressRepository;
import com.Steprella.Steprella.services.abstracts.*;
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
    private final CustomerService customerService;
    private final CityService cityService;
    private final DistrictService districtService;
    private final EntityValidator entityValidator;

    @Override
    public List<ListAddressResponse> getAddresses() {
        Customer customer = customerService.getCustomerOfCurrentUser();
        List<Address> addresses = addressRepository.findByCustomerId(customer.getId());
        return addresses.stream()
                .map(AddressMapper.INSTANCE::listResponseFromAddress)
                .collect(Collectors.toList());
    }

    @Override
    public ListAddressResponse getById(int id) {
        Address address = findAddressAndValidateOwnership(id);
        return AddressMapper.INSTANCE.listResponseFromAddress(address);
    }

    @Override
    public Address getAddressById(int id) {
        return findAddressAndValidateOwnership(id);
    }

    @Override
    public AddAddressResponse add(AddAddressRequest request) {
        Customer customer = customerService.getCustomerOfCurrentUser();
        validateAddressRequest(request.getCityId(), request.getDistrictId());

        Address address = AddressMapper.INSTANCE.addressFromAddRequest(request, customer);
        Address savedAddress = addressRepository.save(address);
        
        return AddressMapper.INSTANCE.addResponseFromAddress(savedAddress);
    }

    @Override
    public UpdateAddressResponse update(UpdateAddressRequest request) {
        Customer customer = customerService.getCustomerOfCurrentUser();
        findAddressAndValidateOwnership(request.getId());
        validateAddressRequest(request.getCityId(), request.getDistrictId());

        Address address = AddressMapper.INSTANCE.addressFromUpdateRequest(request, customer);
        Address savedAddress = addressRepository.save(address);
        
        return AddressMapper.INSTANCE.updateResponseFromAddress(savedAddress);
    }

    @Override
    public void delete(int id) {
        Address address = findAddressAndValidateOwnership(id);
        addressRepository.delete(address);
    }

    private Address findAddressAndValidateOwnership(int id) {
        Customer customer = customerService.getCustomerOfCurrentUser();

        return addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_ADDRESS_NOT_FOUND));
    }

    private void validateAddressRequest(int cityId, int districtId) {
        cityService.getById(cityId);
        districtService.getById(districtId);
        entityValidator.validateCityDistrictRelation(districtId, cityId);
    }
}
