package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.repositories.UserRepository;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.dtos.responses.users.ListUserResponse;
import com.Steprella.Steprella.services.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<ListUserResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<User> users = userRepository.findAll(pageable).getContent();

        return users.stream()
                .map(UserMapper.INSTANCE::listResponseFromUser)
                .collect(Collectors.toList());
    }

    @Override
    public ListUserResponse getResponseById(int id) {
        User user = findUserById(id);
        return UserMapper.INSTANCE.listResponseFromUser(user);
    }

    @Override
    public User getById(int id) {
        return findUserById(id);
    }

    @Override
    public User getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        } else if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return userRepository.findByEmailIgnoreCase(userDetails.getUsername())
                    .orElseThrow(() -> new BusinessException("Kullanıcı bulunamadı."));
        }
        throw new BusinessException("Oturum açmış kullanıcı bilgisi alınamadı.");
    }

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new BusinessException(Messages.Error.CUSTOM_EMAIL_ALREADY_EXISTS));
    }

    @Override
    public User getByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(Messages.Error.CUSTOM_EMAIL_NOT_FOUND));
    }

    @Override
    public void setVerified(String email) {
        User user = getByEmail(email);
        if (user != null) {
            user.setVerified(true);
            userRepository.save(user);
        } else {
            throw new BusinessException(Messages.Error.CUSTOM_USER_NOT_FOUND);
        }
    }

    @Override
    public int getTotalCount() {
        return (int) userRepository.count();
    }

    private User findUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_USER_NOT_FOUND));
    }
}
