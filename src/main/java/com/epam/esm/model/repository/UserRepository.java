package com.epam.esm.model.repository;

import com.epam.esm.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface {@code UserRepository} describes userRepository operations extending JPA repository for working with database tables.
 * @author Oleksandr Myronenko
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * This method is used to get user by name from table.
     * @param name user's name
     * @return user
     */
    Optional<User> findUserByName(String name);
}
