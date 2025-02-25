package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.services.dtos.responses.users.ListUserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<ListUserResponse> getAll(int page, int size);

    ListUserResponse getResponseById(int id);

    User getById(int id);

    User getLoggedInUser();

    User save(User user);

    User getByEmail(String email);

    void setVerified(String email);

    int getTotalCount();

    ListUserResponse getResponseByCustomer(Customer customer);
}
