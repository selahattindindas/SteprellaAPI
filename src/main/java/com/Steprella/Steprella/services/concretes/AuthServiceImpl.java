package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.services.JwtService;
import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.services.abstracts.AuthService;
import com.Steprella.Steprella.services.abstracts.CustomerService;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.abstracts.VerificationCodeService;
import com.Steprella.Steprella.services.dtos.requests.customers.AddCustomerRequest;
import com.Steprella.Steprella.services.dtos.requests.tokens.RefreshTokenRequest;
import com.Steprella.Steprella.services.dtos.requests.users.AddUserRequest;
import com.Steprella.Steprella.services.dtos.requests.users.LoginUserRequest;
import com.Steprella.Steprella.services.dtos.requests.users.UpdateUserRequest;
import com.Steprella.Steprella.services.dtos.responses.tokens.RefreshTokenResponse;
import com.Steprella.Steprella.services.dtos.responses.users.AddUserResponse;
import com.Steprella.Steprella.services.dtos.responses.users.LoginUserResponse;
import com.Steprella.Steprella.services.dtos.responses.users.UpdateUserResponse;
import com.Steprella.Steprella.services.enums.Role;
import com.Steprella.Steprella.services.mappers.AuthMapper;
import com.Steprella.Steprella.services.mappers.UserMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final VerificationCodeService verificationCodeService;
    private final CustomerService customerService;

    @Override
    public AddUserResponse register(AddUserRequest request) {
        User newUser = AuthMapper.INSTANCE.userFromAddRequest(request);

        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(Role.CUSTOMER);
        newUser.setVerified(false);

        User savedUser = userService.save(newUser);

        AddCustomerRequest customerRequest = new AddCustomerRequest();
        customerRequest.setUserId(savedUser.getId());
        customerService.add(customerRequest);

        verificationCodeService.sendActivationEmail(savedUser.getEmail());

        return AuthMapper.INSTANCE.addResponseFromUser(savedUser);
    }

    @Override
    public UpdateUserResponse update(UpdateUserRequest request) {

        User currentUser = userService.getLoggedInUser();
        Customer customer = customerService.getCustomerOfCurrentUser();

        currentUser.setFullName(request.getFullName());
        currentUser.setPhone(request.getPhone());
        currentUser.setGender(request.getGender());

        User savedUser = userService.save(currentUser);
        
        return AuthMapper.INSTANCE.updateResponseFromUser(savedUser);
    }

    @Override
    public LoginUserResponse login(LoginUserRequest request, HttpServletResponse response) {
        User user = userService.getByEmail(request.getEmail());

        if (user.getRole() != Role.CUSTOMER) {
            throw new AccessDeniedException(Messages.Error.ACCESS_DENIED);
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String accessToken = jwtService.generateAccessToken(user, user.getFullName(), user.getRole(), user.getPhone(), response);
        String refreshToken = jwtService.generateRefreshToken(user, user.getFullName(), user.getRole(), user.getPhone(), response);
        long accessTokenExpiration = jwtService.getExpirationTime();

        return new LoginUserResponse(accessToken, refreshToken, accessTokenExpiration);
    }

    @Override
    public LoginUserResponse adminLogin(LoginUserRequest request, HttpServletResponse response) {
        User user = userService.getByEmail(request.getEmail());

        if (user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException(Messages.Error.ACCESS_DENIED);
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String accessToken = jwtService.generateAccessToken(user, user.getFullName(), user.getRole(), user.getPhone(), response);
        String refreshToken = jwtService.generateRefreshToken(user, user.getFullName(), user.getRole(), user.getPhone(), response);
        long accessTokenExpiration = jwtService.getExpirationTime();

        return new LoginUserResponse(accessToken, refreshToken, accessTokenExpiration);
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request, HttpServletResponse response) {
        String refreshToken = request.getRefreshToken();

        if (jwtService.isTokenExpired(refreshToken)) {
            throw new BusinessException(Messages.Error.TOKEN_EXPIRED);
        }

        String username = jwtService.extractUsername(refreshToken);

        User user = userService.getByEmail(username);

        String newAccessToken = jwtService.generateAccessToken(user, user.getFullName(), user.getRole(), user.getPhone(), response);
        String newRefreshToken = jwtService.generateRefreshToken(user, user.getFullName(), user.getRole(), user.getPhone(), response);

        return new RefreshTokenResponse(newAccessToken, newRefreshToken, jwtService.getExpirationTime());
    }
}
