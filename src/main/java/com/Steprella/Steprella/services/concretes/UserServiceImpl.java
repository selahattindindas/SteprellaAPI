package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.repositories.UserRepository;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.dtos.requests.users.AddUserRequest;
import com.Steprella.Steprella.services.dtos.responses.users.AddUserResponse;
import com.Steprella.Steprella.services.dtos.responses.users.ListUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<ListUserResponse> getAll() {
        return List.of();
    }

    @Override
    public ListUserResponse getResponseById(int id) {
        return null;
    }

    @Override
    public User getById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getLoggedInUser() {
        int idOfLoggedInUser = (int) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return getById(idOfLoggedInUser);
    }

    @Override
    public AddUserResponse add(AddUserRequest request) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByEmailIgnoreCase(username).orElse(null);
    }
}
