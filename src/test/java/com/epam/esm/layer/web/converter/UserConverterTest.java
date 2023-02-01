package com.epam.esm.layer.web.converter;

import com.epam.esm.model.entity.User;
import com.epam.esm.web.converter.Converter;
import com.epam.esm.web.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ComponentScan("com.epam.esm.layer")
 class UserConverterTest {
    @Autowired
    private User user;
    @Autowired
    private UserDto userDto;
    @Autowired
    private Converter<User,UserDto> userConverter;
    @Test
    void toModelTest(){
        UserDto userDtoTest = userConverter.toModel(user);
        assertEquals(userDtoTest.getId(),user.getId());
        assertEquals(userDtoTest.getName(),user.getName());
    }
    @Test
    void toEntityTest(){
        User userTest = userConverter.toEntity(userDto);
        assertEquals(userTest.getId(),userDto.getId());
        assertEquals(userTest.getName(),userDto.getName());
    }

}
