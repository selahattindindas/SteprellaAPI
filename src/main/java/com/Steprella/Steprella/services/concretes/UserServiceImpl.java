package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.repositories.UserRepository;
import com.Steprella.Steprella.services.abstracts.AddressService;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.dtos.responses.addresses.ListAddressResponse;
import com.Steprella.Steprella.services.dtos.responses.users.ListUserResponse;
import com.Steprella.Steprella.services.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Lazy
    private final AddressService addressService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy AddressService addressService) {
        this.userRepository = userRepository;
        this.addressService = addressService;
    }

    @Override
    public List<ListUserResponse> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapUserToListUserResponse).collect(Collectors.toList());
    }

    @Override
    public ListUserResponse getResponseById(int id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(Messages.Error.CUSTOM_USER_NOT_FOUND));

        List<ListAddressResponse> addresses = addressService.getAddressesByUserId(user.getId());
        return UserMapper.INSTANCE.listResponseFromUser(user, addresses);
    }

    @Override
    public User getById(int id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(Messages.Error.CUSTOM_USER_NOT_FOUND));
    }

    @Override
    public User getLoggedInUser() {
        int idOfLoggedInUser = (int) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return getById(idOfLoggedInUser);
    }

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_USER_NOT_FOUND));
    }

    private ListUserResponse mapUserToListUserResponse(User user) {
        List<ListAddressResponse> addresses = addressService.getAddressesByUserId(user.getId());
        return UserMapper.INSTANCE.listResponseFromUser(user, addresses);
    }
}
