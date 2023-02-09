package com.epam.esm.service.impl;

import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.RoleRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.AuthenticationService;
import com.epam.esm.service.JwtService;
import com.epam.esm.service.exception.NoSuchEntityException;
import com.epam.esm.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    @Override
    public void register(User user) {
        Optional<Role> role = roleRepository.findById(2);
        if(role.isEmpty()){
            log.error("role does not exist " + role);
            throw new NoSuchEntityException("This role does not exist");
        }
        user.setRole(role.get());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //find all users
        List<User> userList = userRepository.findAll();
        //get last user's id
        Long lastId = userList.stream().reduce((first,second)->second).orElseThrow().getId();
        user.setId(lastId+1);
        userRepository.save(user);
        log.info("successfully registered new user with name " + user.getName());
    }

    @Override
    public UserResponseDto signIn(User user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(),
                user.getPassword()));
        var signUser = userDetailsService.loadUserByUsername(user.getName());
        var jwtToken = jwtService.generateToken(signUser);
        return new UserResponseDto(signUser.getUsername(),
                signUser.getPassword(),jwtToken);
    }
}
