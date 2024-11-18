package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.repositories.CustomerRepository;
import com.Steprella.Steprella.services.abstracts.CustomerService;
import com.Steprella.Steprella.services.dtos.requests.customers.AddCustomerRequest;
import com.Steprella.Steprella.services.dtos.responses.customers.AddCustomerResponse;
import com.Steprella.Steprella.services.dtos.responses.customers.ListCustomerResponse;
import com.Steprella.Steprella.services.enums.Role;
import com.Steprella.Steprella.services.mappers.CustomerMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Customer getById(int id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public ListCustomerResponse getResponseById(int id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        return CustomerMapper.INSTANCE.listResponseFromCustomer(customer);
    }

    @Override
    public Customer getLoggedInCustomer() {
        int idOfLoggedInCustomer = (int) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return getById(idOfLoggedInCustomer);
    }

    @Override
    public AddCustomerResponse add(AddCustomerRequest request) {
        Customer customer = CustomerMapper.INSTANCE.customerFromAddRequest(request);
        customer.setRole(Role.CUSTOMER);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Customer savedCustomer = addCustomer(customer);
        return CustomerMapper.INSTANCE.addResponseFromCustomer(savedCustomer);
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteById(int id) {
        customerRepository.deleteById(id);
    }
}
