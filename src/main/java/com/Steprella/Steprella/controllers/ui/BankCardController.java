package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.BankCardService;
import com.Steprella.Steprella.services.dtos.requests.bankcards.AddBankCardRequest;
import com.Steprella.Steprella.services.dtos.requests.bankcards.UpdateBankCardRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.bankcards.AddBankCardResponse;
import com.Steprella.Steprella.services.dtos.responses.bankcards.ListBankCardResponse;
import com.Steprella.Steprella.services.dtos.responses.bankcards.UpdateBankCardResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank-cards")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
public class BankCardController extends BaseController {

    private final BankCardService bankCardService;

    @GetMapping("/get-bank-cards")
    public ResponseEntity<BaseResponse<List<ListBankCardResponse>>> getBankCards() {
        List<ListBankCardResponse> bankCards = bankCardService.getBankCards();
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, bankCards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ListBankCardResponse>> getById(@PathVariable int id) {
        ListBankCardResponse bankCard = bankCardService.getById(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, bankCard);
    }

    @PostMapping("/create-bank-card")
    public ResponseEntity<BaseResponse<AddBankCardResponse>> add(@RequestBody @Valid AddBankCardRequest request) {
        AddBankCardResponse bankCard = bankCardService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, bankCard);
    }

    @PutMapping("/update-bank-card")
    public ResponseEntity<BaseResponse<UpdateBankCardResponse>> update(@RequestBody @Valid UpdateBankCardRequest request) {
        UpdateBankCardResponse bankCard = bankCardService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, bankCard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable int id) {
        bankCardService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
