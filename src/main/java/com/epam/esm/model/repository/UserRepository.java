package com.epam.esm.model.repository;

import com.epam.esm.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface {@code UserRepository} describes userRepository operations extending JPA repository for working with database tables.
 * @author Oleksandr Myronenko
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
