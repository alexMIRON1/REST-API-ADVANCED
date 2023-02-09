package com.epam.esm.web.controller;

import com.epam.esm.model.entity.User;
import com.epam.esm.service.AuthenticationService;
import com.epam.esm.web.converter.Converter;
import com.epam.esm.web.dto.UserDto;
import com.epam.esm.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class {@code AuthenticationController} represents endpoints of API which allows you to do operations for authentication.
 * @author Oleksandr Myronenko
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final Converter<User,UserDto> userConverter;

    /**
     * This method is used to register new user.
     * @param userDto user
     */
    @PostMapping("/register")
    public void registerUser(@RequestBody UserDto userDto){
        authenticationService.register(userConverter.toEntity(userDto));
    }
    @PostMapping("/signIn")
    public UserResponseDto signIn(@RequestBody UserDto userDto){
        return authenticationService.signIn(userConverter.toEntity(userDto));
    }
}
