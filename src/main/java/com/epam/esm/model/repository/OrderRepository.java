package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface {@code OrderRepository} describes orderRepository operations extending JPA repository for working with database tables.
 * @author Oleksandr Myronenko
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    /**
     * Method for getting list of users by user's id from table.
     * @param id user's id
     * @return list of user
     */
    @Query("select o.user from order o join o.user u where u.id = :id")
    List<User> getAllByUserId(@Param("id") Long id);
}
