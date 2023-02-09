package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface {@code RoleRepository} describes orderRepository operations extending JPA repository for working with database tables.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
}
