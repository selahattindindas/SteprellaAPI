package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.services.abstracts.AuthService;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.dtos.requests.users.AddUserRequest;
import com.Steprella.Steprella.services.dtos.requests.users.LoginUserRequest;
import com.Steprella.Steprella.services.dtos.responses.users.AddUserResponse;
import com.Steprella.Steprella.services.enums.Role;
import com.Steprella.Steprella.services.mappers.AuthMapper;
import com.Steprella.Steprella.services.rules.UserRules;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRules userRules;
    private final PasswordEncoder passwordEncoder;
    private UserService userService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AddUserResponse register(AddUserRequest request) {

        User newUser = AuthMapper.INSTANCE.userFromAddRequest(request);

        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(Role.CUSTOMER);

        User savedUser = userService.add(newUser);

        return AuthMapper.INSTANCE.addResponseFromUser(savedUser);
    }

    @Override
    public User login(LoginUserRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        return userService.getByEmail(request.getEmail());
    }
}
