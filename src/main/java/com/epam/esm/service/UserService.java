package com.epam.esm.service;

import com.epam.esm.model.entity.User;
/**
 * Interface {@code TagService} describes abstract behavior for working with {@link User} objects.
 * @author Oleksandr Myronenko
 */

public interface UserService extends CRUDService<User>{
    /**
     * Method for getting user by highest cost of all orders
     * @return user
     */
    User getUserByHighestCostOfOrders();
}
