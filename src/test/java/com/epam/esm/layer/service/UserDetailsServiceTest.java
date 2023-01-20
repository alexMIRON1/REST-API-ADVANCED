package com.epam.esm.layer.service;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
class UserDetailsServiceTest {
    @Autowired
    private UserDetailsService userDetailsService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private User user;
    @Test
    void loadByUsernameTest(){
        when(userRepository.findUserByName(user.getName())).thenReturn(Optional.of(user));
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());
        assertEquals(userDetails.getUsername(),user.getName());
        assertEquals(userDetails.getPassword(),user.getPassword());
    }
    @Test
    void loadByUsernameWrongUsernameTest(){
        when(userRepository.findUserByName(user.getName())).thenReturn(Optional.of(user));
        assertThrows(UsernameNotFoundException.class,()->userDetailsService.loadUserByUsername("Oleg"));
    }
}
