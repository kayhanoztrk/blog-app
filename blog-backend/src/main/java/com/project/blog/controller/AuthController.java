package com.project.blog.controller;

import com.project.blog.entity.User;
import com.project.blog.model.request.UserCreateRequest;
import com.project.blog.model.request.UserRequest;
import com.project.blog.model.response.AuthResponse;
import com.project.blog.model.response.UserResponse;
import com.project.blog.security.JwtTokenProvider;
import com.project.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.project.blog.controller.PostController.logger;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          PasswordEncoder passwordEncoder,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication auth = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        User user = userService.findByUsername(request.getUsername());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken("Bearer " + jwtToken);
        authResponse.setUserId(user.getId());

        logger.info("authMessage" + authResponse.getMessage());

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRequest request) {
        AuthResponse authResponse = new AuthResponse();
        if (userService.findByUsername(request.getUsername()) != null) {
            authResponse.setMessage("username already in use");
        } else {
            UserCreateRequest createRequest = new UserCreateRequest();
            createRequest.setUsername(request.getUsername());
            createRequest.setPassword(passwordEncoder.encode(request.getPassword()));
            userService.createUser(createRequest);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            Authentication auth = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            String jwtToken = jwtTokenProvider.generateJwtToken(auth);
            authResponse.setAccessToken("Bearer " + jwtToken);
            //authResponse.setUserId(createRequest.getId());
            authResponse.setMessage("user has been created successfully");
        }
        logger.info("authResponseGetMessage" + authResponse.getMessage());
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }
}
