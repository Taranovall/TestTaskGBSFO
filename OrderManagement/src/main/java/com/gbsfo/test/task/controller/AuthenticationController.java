package com.gbsfo.test.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbsfo.test.task.payload.response.JwtResponse;
import com.gbsfo.test.task.payload.request.UserRequest;
import com.gbsfo.test.task.payload.response.UserResponse;
import com.gbsfo.test.task.entity.User;
import com.gbsfo.test.task.exception.IncorrectPasswordException;
import com.gbsfo.test.task.service.UserService;
import com.gbsfo.test.task.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.gbsfo.test.task.util.Constant.INCORRECT_PASSWORD_ERROR_MESSAGE;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @PostMapping("/sign_up")
    public ResponseEntity<JwtResponse> signUp(@RequestBody @Valid UserRequest userRequest) {
        User user = objectMapper.convertValue(userRequest, User.class);

        userService.createUser(user);

        String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(
                token,
                new UserResponse(user.getUsername(), user.getRole(), user.getOrders())
        ));
    }

    @PostMapping("/sign_in")
    public ResponseEntity<JwtResponse> signIn(@RequestBody @Valid UserRequest userRequest) {
        User user = userService.findByUsername(userRequest.getUsername());

        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw new IncorrectPasswordException(INCORRECT_PASSWORD_ERROR_MESSAGE);
        }

        String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(
                token,
                new UserResponse(user.getUsername(), user.getRole(), user.getOrders())
        ));
    }
}
