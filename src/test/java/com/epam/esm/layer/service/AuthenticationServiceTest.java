package com.epam.esm.layer.service;

import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.RoleRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.AuthenticationService;
import com.epam.esm.web.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
 class AuthenticationServiceTest{
    @Autowired
    private AuthenticationService authenticationService;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @Autowired
    private User user;


    @Test
    void registerTest() {
        Role role = new Role(1, "user", new ArrayList<>());
        when(roleRepository.findById(2)).thenReturn(Optional.of(role));
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        when(userRepository.save(user)).thenReturn(user);
        User userTest = authenticationService.register(user);
        assertEquals(userTest.getId(), user.getId());
        assertEquals(userTest.getName(), user.getName());
        assertEquals(userTest.getPassword(), user.getPassword());
        assertEquals(userTest.getOrders(), user.getOrders());
        assertEquals(userTest.getRole().getId(), user.getRole().getId());
    }
    @Test
    void signInTest(){
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(),
                user.getRole()))).thenReturn(null);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName())));
        when(userDetailsService.loadUserByUsername(user.getName())).thenReturn(userDetails);
        UserResponseDto userResponseDto = authenticationService.signIn(user);
        assertEquals(userResponseDto.getName(),user.getName());
        assertEquals(userResponseDto.getPassword(),user.getPassword());
    }

}
