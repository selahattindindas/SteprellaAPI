package com.Steprella.Steprella.services.rules;

import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.services.abstracts.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class UserRules {

    private final UserService userService;

    public UserRules(@Lazy UserService userService) {
        this.userService = userService;
    }

    public void userWithSameEmailShouldNotExist(String email) {
        User user = (User) userService.loadUserByUsername(email);
        if (user != null)
            throw new BusinessException(Messages.Error.USER_WITH_SAME_EMAIL_EXISTS);
    }
}
