package com.epam.esm.service;

import org.springframework.data.domain.Page;

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
     * Method for returning page of objects
     * @param page number ob page
     * @param size number of size in page
     * @return page of objects
     */
    Page<T> getAll(int page, int size);

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
