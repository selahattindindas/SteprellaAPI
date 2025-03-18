package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.BankCard;
import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.repositories.BankCardRepository;
import com.Steprella.Steprella.services.abstracts.BankCardService;
import com.Steprella.Steprella.services.abstracts.CustomerService;
import com.Steprella.Steprella.services.dtos.requests.bankcards.AddBankCardRequest;
import com.Steprella.Steprella.services.dtos.requests.bankcards.UpdateBankCardRequest;
import com.Steprella.Steprella.services.dtos.responses.bankcards.AddBankCardResponse;
import com.Steprella.Steprella.services.dtos.responses.bankcards.ListBankCardResponse;
import com.Steprella.Steprella.services.dtos.responses.bankcards.UpdateBankCardResponse;
import com.Steprella.Steprella.services.mappers.BankCardMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BankCardServiceImpl implements BankCardService {

    private final BankCardRepository bankCardRepository;
    private final CustomerService customerService;

    @Override
    public List<ListBankCardResponse> getBankCards() {
        Customer customer = customerService.getCustomerOfCurrentUser();
        return customer.getBankCards().stream()
                .map(BankCardMapper.INSTANCE::listResponseFromBankCard)
                .toList();
    }

    @Override
    public ListBankCardResponse getById(int id) {
        BankCard bankCard = findBankCardById(id);
        return BankCardMapper.INSTANCE.listResponseFromBankCard(bankCard);
    }

    @Override
    public AddBankCardResponse add(AddBankCardRequest request) {
        Customer customer = customerService.getCustomerOfCurrentUser();

        BankCard bankCard = BankCardMapper.INSTANCE.bankCardFromAddRequest(request, customer);
        bankCard = bankCardRepository.save(bankCard);

        return BankCardMapper.INSTANCE.addResponseFromBankCard(bankCard);
    }

    @Override
    public UpdateBankCardResponse update(UpdateBankCardRequest request) {
        Customer customer = customerService.getCustomerOfCurrentUser();
        findBankCardById(request.getId());

        BankCard bankCard = BankCardMapper.INSTANCE.bankCardFromUpdateRequest(request, customer);
        bankCard = bankCardRepository.save(bankCard);

        return BankCardMapper.INSTANCE.updateResponseFromBankCard(bankCard);
    }

    @Override
    public void delete(int id) {

    }

    private BankCard findBankCardById(int id) {
        return bankCardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_BANK_CARD_NOT_FOUND));
    }
}
