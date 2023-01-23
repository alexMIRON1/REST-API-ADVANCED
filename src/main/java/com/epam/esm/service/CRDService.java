package com.epam.esm.service;

import java.util.List;

/**
 * Interface {@code CRDService} describes CRD operations for working with objects.
 * @param <T> the type parameter
 * @author Oleksandr Myronenko
 */
public interface CRDService<T> {
    /**
     * Method for getting an entity object by ID.
     * @param id ID of entity object
     * @return Entity object
     */
    T getById(Long id);

    /**
     * Method for getting all entities.
     * @return List of all entities
     */
    List<T> getAll();

    /**
     * Method for saving an entity.
     * @param model model to save
     */
    void insert(T model);


    /**
     * Method for removing an entity
     * @param item model to remove
     */
    void remove(T item);
}
