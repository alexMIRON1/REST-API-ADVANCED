package com.epam.esm.web.mapper;

import com.epam.esm.model.entity.User;
import com.epam.esm.web.dto.UserDto;
import org.mapstruct.Mapper;

/**
 * Interface {@code UserMapper} describes converting from dto {@link UserDto} to entity {@link User} and vice versa.
 * @author Oleksandr Myronenko
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    /**
     * Method for converting entity user to user dto
     * @param user entity user
     * @return user dto
     */
    UserDto toDto(User user);

    /**
     * Method for converting user dto to entity
     * @param userDto user dto
     * @return entity user
     */
    User toEntity(UserDto userDto);
}
