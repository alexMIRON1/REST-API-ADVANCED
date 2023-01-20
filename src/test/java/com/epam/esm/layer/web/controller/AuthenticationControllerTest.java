package com.epam.esm.layer.web.controller;

import com.epam.esm.model.entity.User;
import com.epam.esm.service.AuthenticationService;
import com.epam.esm.web.controller.AuthenticationController;
import com.epam.esm.web.converter.Converter;
import com.epam.esm.web.dto.UserDto;
import com.epam.esm.web.dto.UserResponseDto;
import com.epam.esm.web.filter.JwtAuthFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@ComponentScan("com.epam.esm.layer")
 class AuthenticationControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private JacksonTester<UserDto> jacksonTesterUser;
    private JacksonTester<UserResponseDto> jacksonTesterResponseUser;
    private MockMvc mockMvc;
    @MockBean
    private JwtAuthFilter jwtAuthFilter;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private Converter<User,UserDto> userConverter;
    @Autowired
    private User user;
    @Autowired
    private UserDto userDto;
    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        JacksonTester.initFields(this,new ObjectMapper());
    }
    @Test
    @SneakyThrows
    void registerUserTest(){
        when(userConverter.toEntity(userDto)).thenReturn(user);
        when(authenticationService.register(user)).thenReturn(user);
        when(userConverter.toModel(user)).thenReturn(userDto);
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTesterUser.write(userDto).getJson())).andExpect(status().isOk());
    }
    @Test
    @SneakyThrows
    void signInTest(){
        UserResponseDto userResponseDto = new UserResponseDto(user.getName(),user.getPassword(), "12312ada");
        when(userConverter.toEntity(userDto)).thenReturn(user);
        when(authenticationService.signIn(user)).thenReturn(userResponseDto);
        mockMvc.perform(post("/auth/signIn").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTesterResponseUser.write(userResponseDto).getJson())).andExpect(status().isOk());
    }
}
