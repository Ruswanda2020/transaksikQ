package com.Onedev.transaksiQ.service.impl;

import com.Onedev.transaksiQ.dto.membersip.RegisterRequest;
import com.Onedev.transaksiQ.entity.Role;
import com.Onedev.transaksiQ.entity.User;
import com.Onedev.transaksiQ.exception.TransactionApiException;
import com.Onedev.transaksiQ.repository.RoleRepository;
import com.Onedev.transaksiQ.repository.UserRepository;
import com.Onedev.transaksiQ.security.JwtTokenProvider;
import com.Onedev.transaksiQ.service.AuthService;
import com.Onedev.transaksiQ.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import com.Onedev.transaksiQ.dto.membersip.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final TransactionService transactionService;

    @Override
    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);

    }

    @Override
    public String Register(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw new TransactionApiException(HttpStatus.BAD_REQUEST, "Email sudah terdaftar.", 102);
        }

        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());

        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        User userSaved = userRepository.save(user);
        transactionService.insertBalance(userSaved.getId(), 0);


        return "User registered successfully!.";
    }

    @Override
    public User getEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }
}
