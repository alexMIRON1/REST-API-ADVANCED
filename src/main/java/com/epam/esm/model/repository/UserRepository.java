package com.epam.esm.model.repository;

import com.epam.esm.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Interface {@code UserRepository} describes userRepository operations extending JPA repository for working with database tables.
 * @author Oleksandr Myronenko
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * Method for getting user by highest cost of all orders from table.
     * @return user
     */
    @Query("select o.user from order o group by o.user order by sum(o.price) desc ")
    User getUserByHighestCostOfOrders();
}
