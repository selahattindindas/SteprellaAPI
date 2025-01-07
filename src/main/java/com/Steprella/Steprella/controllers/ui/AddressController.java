package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.AddressService;
import com.Steprella.Steprella.services.dtos.requests.addresses.AddAddressRequest;
import com.Steprella.Steprella.services.dtos.requests.addresses.UpdateAddressRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.addresses.AddAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.addresses.ListAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.addresses.UpdateAddressResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
public class AddressController extends BaseController {

    private final AddressService addressService;

    @GetMapping("/by-user-id/{userId}")
    public ResponseEntity<BaseResponse<List<ListAddressResponse>>> getAddressesByUserId(@PathVariable int userId){
        List<ListAddressResponse> addresses = addressService.getAddressesByUserId(userId);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, addresses, null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListAddressResponse>> getById(@PathVariable int id){
        ListAddressResponse address = addressService.getById(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, address, null);
    }

    @PostMapping("/create-address")
    public ResponseEntity<BaseResponse<AddAddressResponse>> add(@RequestBody @Valid AddAddressRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null, null);

        AddAddressResponse address = addressService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, address, null);
    }

    @PutMapping("/update-address")
    public ResponseEntity<BaseResponse<UpdateAddressResponse>> update(@RequestBody @Valid UpdateAddressRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null, null);

        UpdateAddressResponse address = addressService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, address, null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        addressService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null, null);
    }
}
