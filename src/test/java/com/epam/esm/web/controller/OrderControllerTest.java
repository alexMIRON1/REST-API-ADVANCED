package com.epam.esm.web.controller;

import com.epam.esm.model.entity.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.web.controller.OrderController;
import com.epam.esm.web.dto.OrderDto;
import com.epam.esm.web.converter.Converter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(OrderController.class)
@ComponentScan("com.epam.esm.web")
 class OrderControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private JacksonTester<OrderDto> jacksonTester;
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @MockBean
    private Converter<Order,OrderDto> orderConverter;
    @Autowired
    private Order order;
    @Autowired
    private OrderDto orderDto;
    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        JacksonTester.initFields(this,new ObjectMapper());
    }
    @Test
    @SneakyThrows
    void getByIdTest(){
        when(orderService.getById(order.getId())).thenReturn(order);
        when(orderConverter.toModel(order)).thenReturn(orderDto);
        mockMvc.perform(get("/orders/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id",is(order.getId().intValue())));
    }
    @Test
    @SneakyThrows
    void getOrdersByUserIdTest(){
        when(orderConverter.toModel(order)).thenReturn(orderDto);
        when(orderService.getAllOrdersByUserId(1L)).thenReturn(List.of(order));
        mockMvc.perform(get("/orders/user/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].id",is(order.getId().intValue())));
    }
}
