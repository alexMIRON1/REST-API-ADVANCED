package com.epam.esm.web.mapper;

import com.epam.esm.model.entity.Order;
import com.epam.esm.web.dto.OrderDto;
import org.mapstruct.Mapper;

/**
 * Interface {@code OrderMapper} describes converting from dto {@link OrderDto} to entity {@link Order} and vice versa.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {
    /**
     * Method for converting entity order to order dto
     * @param order entity order
     * @return order dto
     */
    OrderDto toDto(Order order);

    /**
     * Method for converting order dto to entity order
     * @param orderDto order dto
     * @return entity order
     */
    Order toEntity(OrderDto orderDto);
}
