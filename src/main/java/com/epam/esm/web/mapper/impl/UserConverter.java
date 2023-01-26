package com.epam.esm.web.mapper.impl;

import com.epam.esm.model.entity.User;
import com.epam.esm.web.dto.UserDto;
import com.epam.esm.web.mapper.Converter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserConverter implements Converter<User, UserDto> {
    private final ModelMapper modelMapper;
    @Override
    public @NonNull UserDto toModel(@NonNull User entity) {
        UserDto userDto = modelMapper.map(entity,UserDto.class);
        log.info("successfully converted user entity " + entity + " to user dto " + userDto);
        return userDto;
    }

    @Override
    public User toEntity(UserDto dto) {
        User user = modelMapper.map(dto,User.class);
        log.info("successfully converted user dto " + dto +  " to user entity " + user);
        return user;
    }
}
