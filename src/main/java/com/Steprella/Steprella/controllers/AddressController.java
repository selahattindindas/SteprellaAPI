package com.Steprella.Steprella.controllers;

import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.AddressService;
import com.Steprella.Steprella.services.abstracts.CityService;
import com.Steprella.Steprella.services.abstracts.DistrictService;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.dtos.requests.addresses.AddAddressRequest;
import com.Steprella.Steprella.services.dtos.requests.addresses.UpdateAddressRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.addresses.AddAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.addresses.ListAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.addresses.UpdateAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.users.ListUserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController extends BaseController {

    private final AddressService addressService;
    private final UserService userService;
    private final CityService cityService;
    private final DistrictService districtService;

    @GetMapping("/by-user-id/{userId}")
    public ResponseEntity<BaseResponse<List<ListAddressResponse>>> getAddressesByUserId(@PathVariable int userId){
        ListUserResponse user = userService.getResponseById(userId);
        if (user == null) {
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_USER_NOT_FOUND, null);
        }

        List<ListAddressResponse> addresses = addressService.getAddressesByUserId(userId);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, addresses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListAddressResponse>> getById(@PathVariable int id){
        ListAddressResponse address = addressService.getById(id);
        if(address == null)
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_ADDRESS_NOT_FOUND, null);
        else
            return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, address);
    }

    @PostMapping("/create-address")
    public ResponseEntity<BaseResponse<AddAddressResponse>> add(@RequestBody @Valid AddAddressRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || addressService.isDistrictBelongsToCity(request.getDistrictId(), request.getCityId()))
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);

        if (userService.getById(request.getUserId()) == null)
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_USER_NOT_FOUND, null);

        if (districtService.getById(request.getDistrictId()) == null)
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_DISTRICT_NOT_FOUND, null);

        if (cityService.getById(request.getCityId()) == null)
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_CITY_NOT_FOUND, null);


        AddAddressResponse address = addressService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, address);
    }

    @PutMapping("/update-address")
    public ResponseEntity<BaseResponse<UpdateAddressResponse>> update(@RequestBody @Valid UpdateAddressRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || addressService.isDistrictBelongsToCity(request.getDistrictId(), request.getCityId()))
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);

        if(addressService.getById(request.getId()) == null)
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_ADDRESS_NOT_FOUND, null);

        if (userService.getById(request.getUserId()) == null)
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_USER_NOT_FOUND, null);

        if (districtService.getById(request.getDistrictId()) == null)
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_DISTRICT_NOT_FOUND, null);

        if (cityService.getById(request.getCityId()) == null)
            return sendResponse(HttpStatus.NOT_FOUND, Messages.Error.CUSTOM_CITY_NOT_FOUND, null);


        UpdateAddressResponse address = addressService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, address);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        addressService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
