package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.services.dtos.requests.customers.AddCustomerRequest;
import com.Steprella.Steprella.services.dtos.responses.customers.AddCustomerResponse;
import com.Steprella.Steprella.services.dtos.responses.customers.ListCustomerResponse;

public interface CustomerService {

    ListCustomerResponse getById(int id);

    Customer getCustomerById(int id);

    Customer getCustomerOfCurrentUser();

    AddCustomerResponse add(AddCustomerRequest request);

    void delete(int id);

    boolean existsByUserId(int userId);
}
