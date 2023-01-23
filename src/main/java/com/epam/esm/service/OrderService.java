package com.epam.esm.service;

import com.epam.esm.model.entity.Order;

import java.util.List;

/**
 * Interface {@code TagService} describes abstract behavior for working with {@link Order} objects.
 * @author Oleksandr Myronenko
 */
public interface OrderService extends CRDService<Order>{
    /**
     * Method for getting list of orders by user's id
     * @param id user's id
     * @return list of orders
     */
    List<Order> getAllOrdersByUserId(Long id);
}
