package com.epam.esm.layer.web.converter;

import com.epam.esm.model.entity.Order;
import com.epam.esm.web.converter.Converter;
import com.epam.esm.web.dto.OrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ComponentScan("com.epam.esm.layer")
class OrderConverterTest {
    @Autowired
    private Order order;
    @Autowired
    private OrderDto orderDto;
    @Autowired
    private Converter<Order,OrderDto> orderConverter;
    @Test
    void toModelTest(){
        OrderDto orderDtoTest = orderConverter.toModel(order);
        assertEquals(orderDtoTest.getId(),order.getId());
        assertEquals(orderDtoTest.getPrice(),order.getPrice());
        assertEquals(orderDtoTest.getPurchaseTime(),order.getPurchaseTime());
    }
    @Test
    void toEntityTest(){
        Order orderTest = orderConverter.toEntity(orderDto);
        assertEquals(orderTest.getId(),orderDto.getId());
        assertEquals(orderTest.getPrice(),orderDto.getPrice());
        assertEquals(orderTest.getPurchaseTime(),orderDto.getPurchaseTime());
    }
}
