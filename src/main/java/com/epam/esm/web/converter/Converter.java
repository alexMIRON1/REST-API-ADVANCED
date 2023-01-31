package com.epam.esm.web.converter;


import lombok.NonNull;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

/**
 * Interface {@code Converter} describes converting from dto to entity and vice versa.
 * @param <T> object's entity
 * @param <V> object's dto
 */
public interface Converter<T,V extends RepresentationModel<?>> extends RepresentationModelAssembler<T,V> {
    /**
     * Method for converting from entity to dto
     * @param entity object's entity
     * @return object's dto
     */
    @NonNull
    V toModel(@NonNull T entity);

    /**
     * Method for converting from dto to entity
     * @param dto object's dto
     * @return object's entity
     */
    T toEntity(V dto);
}
