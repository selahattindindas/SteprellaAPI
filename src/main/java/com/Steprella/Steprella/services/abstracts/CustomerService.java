package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.services.dtos.requests.customers.AddCustomerRequest;
import com.Steprella.Steprella.services.dtos.responses.customers.AddCustomerResponse;
import com.Steprella.Steprella.services.dtos.responses.customers.ListCustomerResponse;

public interface CustomerService {

    Customer getById(int id);

    ListCustomerResponse getResponseById(int id);

    Customer getLoggedInCustomer();

    AddCustomerResponse add(AddCustomerRequest request);

    Customer addCustomer(Customer customer);

    void deleteById(int id);
}
