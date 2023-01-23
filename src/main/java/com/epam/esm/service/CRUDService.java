package com.epam.esm.service;
/**
 *Interface {@code CRUDService} describes CRUD operations for working with objects.
 * @param <T> the type parameter
 * @author Oleksandr Myronenko
 */
public interface CRUDService<T> extends CRDService<T>{
    /**
     * Method for updating an entity
     * @param entity entity for update
     */
    void update(T entity);
}
