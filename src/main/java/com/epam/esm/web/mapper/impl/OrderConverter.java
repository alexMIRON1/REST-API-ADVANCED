package com.epam.esm.web.mapper.impl;

import com.epam.esm.model.entity.Order;
import com.epam.esm.web.dto.OrderDto;
import com.epam.esm.web.mapper.Converter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class OrderConverter implements Converter<Order, OrderDto> {
    private final ModelMapper modelMapper;
    @Override
    public @NonNull OrderDto toModel(@NonNull Order entity) {
        OrderDto orderDto = modelMapper.map(entity,OrderDto.class);
        log.info("successfully converted entity " + entity + " to order dto");
        return orderDto;
    }

    @Override
    public Order toEntity(OrderDto dto) {
        Order order = modelMapper.map(dto,Order.class);
        log.info("successfully converted dto " +dto + " to entity " + order);
        return order;
    }
}
