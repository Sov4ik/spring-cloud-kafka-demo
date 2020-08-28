package com.bookshop.apigateway.service;

import com.bookshop.apigateway.models.ERole;
import com.bookshop.apigateway.models.Role;
import com.bookshop.apigateway.models.User;
import com.bookshop.apigateway.payload.request.LoginRequest;
import com.bookshop.apigateway.payload.request.SignupRequest;
import com.bookshop.apigateway.payload.response.JwtResponse;
import com.bookshop.apigateway.payload.response.MessageResponse;
import com.bookshop.apigateway.repository.UserRepository;
import com.bookshop.apigateway.security.jwt.JwtUtils;
import com.bookshop.apigateway.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager,
                       PasswordEncoder encoder,
                       JwtUtils jwtUtils,
                       UserRepository userRepository) {

        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    public ResponseEntity<?> registerUser(SignupRequest signUpRequest)  {

        if ( userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if ( userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        createNewUser(signUpRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private void createNewUser(SignupRequest signUpRequest){

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = new Role();
            userRole.setName(ERole.ROLE_USER);
        }

        user.setRoles(roles);
        userRepository.save(user);
    }
}
