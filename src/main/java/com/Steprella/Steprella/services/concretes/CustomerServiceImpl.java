package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.repositories.CustomerRepository;
import com.Steprella.Steprella.services.abstracts.CustomerService;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.abstracts.CartService;
import com.Steprella.Steprella.services.dtos.requests.customers.AddCustomerRequest;
import com.Steprella.Steprella.services.dtos.responses.customers.AddCustomerResponse;
import com.Steprella.Steprella.services.dtos.responses.customers.ListCustomerResponse;
import com.Steprella.Steprella.services.mappers.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            UserService userService,
            @Lazy CartService cartService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.cartService = cartService;
    }

    @Override
    public ListCustomerResponse getById(int id) {
        Customer customer = findCustomerById(id);
        validateCustomerAccess(customer);
        return CustomerMapper.INSTANCE.listResponseFromCustomer(customer);
    }

    @Override
    public Customer getCustomerById(int id) {
        Customer customer = findCustomerById(id);
        validateCustomerAccess(customer);
        return customer;
    }

    @Override
    public Customer getCustomerOfCurrentUser() {
        int userId = userService.getLoggedInUser().getId();
        return customerRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Müşteri bulunamadı."));
    }

    @Override
    public AddCustomerResponse add(AddCustomerRequest request) {
        if (request == null) {
            throw new BusinessException(Messages.Error.CUSTOM_CUSTOMER_REQUIRED);
        }

        if (existsByUserId(request.getUserId())) {
            throw new BusinessException(Messages.Error.CUSTOM_CUSTOMER_ALREADY_EXISTS);
        }

        validateCustomerRequest(request);

        Customer customer = CustomerMapper.INSTANCE.customerFromAddRequest(request);
        Customer savedCustomer = customerRepository.save(customer);

        cartService.createCart(savedCustomer);

        return CustomerMapper.INSTANCE.addResponseFromCustomer(savedCustomer);
    }

    @Override
    public void delete(int id) {
        Customer customer = findCustomerById(id);
        validateCustomerAccess(customer);
        customerRepository.delete(customer);
    }

    @Override
    public boolean existsByUserId(int userId) {
        return customerRepository.existsByUserId(userId);
    }

    private Customer findCustomerById(int id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_CUSTOMER_NOT_FOUND));
    }

    private void validateCustomerRequest(AddCustomerRequest request) {
        if (request.getUserId() <= 0) {
            throw new BusinessException(Messages.Error.CUSTOM_CUSTOMER_INVALID);
        }

        userService.getById(request.getUserId());
    }

    private void validateCustomerAccess(Customer customer) {
        int currentUserId = userService.getLoggedInUser().getId();
        if (customer.getUser().getId() != currentUserId) {
            throw new BusinessException(Messages.Error.CUSTOM_CUSTOMER_ACCESS_DENIED);
        }
    }
}
