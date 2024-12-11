package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.services.JwtService;
import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.services.abstracts.AuthService;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.abstracts.VerificationCodeService;
import com.Steprella.Steprella.services.dtos.requests.tokens.RefreshTokenRequest;
import com.Steprella.Steprella.services.dtos.requests.users.AddUserRequest;
import com.Steprella.Steprella.services.dtos.requests.users.LoginUserRequest;
import com.Steprella.Steprella.services.dtos.responses.tokens.RefreshTokenResponse;
import com.Steprella.Steprella.services.dtos.responses.users.AddUserResponse;
import com.Steprella.Steprella.services.dtos.responses.users.LoginUserResponse;
import com.Steprella.Steprella.services.enums.Role;
import com.Steprella.Steprella.services.mappers.AuthMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final VerificationCodeService verificationCodeService;

    @Override
    public AddUserResponse register(AddUserRequest request) {

        User newUser = AuthMapper.INSTANCE.userFromAddRequest(request);

        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(Role.CUSTOMER);
        newUser.setVerified(false);

        User savedUser = userService.add(newUser);

        verificationCodeService.sendActivationEmail(savedUser.getEmail());

        return AuthMapper.INSTANCE.addResponseFromUser(savedUser);
    }

    @Override
    public LoginUserResponse login(LoginUserRequest request) {
        User user = userService.getByEmail(request.getEmail());

        if (!user.isVerified()) {
            throw new BusinessException(Messages.Error.ACCOUNT_NOT_ACTIVATED);
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String accessToken = jwtService.generateAccessToken(user, user.getFullName(), user.getRole(), user.getPhone());
        String refreshToken = jwtService.generateRefreshToken(user, user.getFullName(), user.getRole(), user.getPhone());
        long accessTokenExpiration = jwtService.getExpirationTime();

        return new LoginUserResponse(accessToken, refreshToken, accessTokenExpiration);
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (jwtService.isTokenExpired(refreshToken)) {
            throw new BusinessException(Messages.Error.TOKEN_EXPIRED);
        }

        String username = jwtService.extractUsername(refreshToken);

        User user = userService.getByEmail(username);

        String newAccessToken = jwtService.generateAccessToken(user, user.getFullName(), user.getRole(), user.getPhone());
        String newRefreshToken = jwtService.generateRefreshToken(user, user.getFullName(), user.getRole(), user.getPhone());

        return new RefreshTokenResponse(newAccessToken, newRefreshToken, jwtService.getExpirationTime());
    }
}
