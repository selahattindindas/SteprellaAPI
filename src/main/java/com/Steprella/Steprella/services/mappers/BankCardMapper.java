package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.BankCard;
import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.services.dtos.requests.bankcards.AddBankCardRequest;
import com.Steprella.Steprella.services.dtos.requests.bankcards.UpdateBankCardRequest;
import com.Steprella.Steprella.services.dtos.responses.bankcards.AddBankCardResponse;
import com.Steprella.Steprella.services.dtos.responses.bankcards.ListBankCardResponse;
import com.Steprella.Steprella.services.dtos.responses.bankcards.UpdateBankCardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BankCardMapper {

    BankCardMapper INSTANCE = Mappers.getMapper(BankCardMapper.class);

    ListBankCardResponse listResponseFromBankCard(BankCard bankCard);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", source = "customer")
    BankCard bankCardFromAddRequest(AddBankCardRequest request, Customer customer);

    @Mapping(target = "id", source = "request.id")
    @Mapping(target = "customer", source = "customer")
    BankCard bankCardFromUpdateRequest(UpdateBankCardRequest request, Customer customer);

    AddBankCardResponse addResponseFromBankCard(BankCard bankCard);

    UpdateBankCardResponse updateResponseFromBankCard(BankCard bankCard);
}
