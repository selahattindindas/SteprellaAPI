package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.bankcards.AddBankCardRequest;
import com.Steprella.Steprella.services.dtos.requests.bankcards.UpdateBankCardRequest;
import com.Steprella.Steprella.services.dtos.responses.bankcards.AddBankCardResponse;
import com.Steprella.Steprella.services.dtos.responses.bankcards.ListBankCardResponse;
import com.Steprella.Steprella.services.dtos.responses.bankcards.UpdateBankCardResponse;

import java.util.List;

public interface BankCardService {

    List<ListBankCardResponse> getBankCards();

    ListBankCardResponse getById(int id);

    AddBankCardResponse add(AddBankCardRequest request);

    UpdateBankCardResponse update(UpdateBankCardRequest request);

    void delete(int id);
}
