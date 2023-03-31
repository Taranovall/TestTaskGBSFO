package com.gbsfo.test.task.service.impl;

import com.gbsfo.test.task.entity.Role;
import com.gbsfo.test.task.entity.User;
import com.gbsfo.test.task.exception.UserAlreadyExistsException;
import com.gbsfo.test.task.exception.UserNotFoundException;
import com.gbsfo.test.task.repository.UserRepository;
import com.gbsfo.test.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.gbsfo.test.task.util.Constant.USERNAME_EXISTS_ERROR_MESSAGE;
import static com.gbsfo.test.task.util.Constant.USER_NOT_FOUND_BY_ID_ERROR_MESSAGE;
import static com.gbsfo.test.task.util.Constant.USER_NOT_FOUND_BY_USERNAME_ERROR_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());

        if (foundUser.isPresent()) {
            String message = String.format(USERNAME_EXISTS_ERROR_MESSAGE, user.getUsername());
            throw new UserAlreadyExistsException(message);
        }

        user.setRole(Role.USER)
                .setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        return user.orElseThrow(() -> {
            String message = String.format(USER_NOT_FOUND_BY_USERNAME_ERROR_MESSAGE, username);
            throw new UserNotFoundException(message);
        });
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            String message = String.format(USER_NOT_FOUND_BY_ID_ERROR_MESSAGE, id);
            throw new UserNotFoundException(message);
        });
    }
}
