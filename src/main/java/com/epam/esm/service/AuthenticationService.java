package com.epam.esm.service;

import com.epam.esm.model.entity.User;
import com.epam.esm.web.dto.UserResponseDto;

/**
 * This interface {@code AuthenticationService} described abstract behavior working for authentication user.
 * @author Oleksandr Myronenko
 */
public interface AuthenticationService {
    /**
     * This method is used to register user.
     * @param user user
     * @return created user
     */
    User register(User user);

    /**
     * This method is used to sign in user.
     * @param user user
     * @return user response dto
     */
    UserResponseDto signIn(User user);
}
