package com.epam.esm.layer.web.controller;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.hateoas.PagedModel.PageMetadata;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(OrderController.class)
@ComponentScan("com.epam.esm.layer")
 class OrderControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private JacksonTester<OrderDto> jacksonTester;
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @MockBean
    private Converter<Order,OrderDto> orderConverter;
    @MockBean
    private PagedResourcesAssembler<Order> pagedResourcesAssembler;
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
    void getAllTest(){
        PageMetadata metadata =  new PageMetadata(10, 1,200);
        int page = 1;
        int size = 10;
        Page<Order> orderPage = new PageImpl<>(List.of(order));
        when(orderService.getAll(page,size)).thenReturn(orderPage);
        PagedModel<OrderDto> orderDtoPagedModel =PagedModel.of(List.of(orderDto),
                metadata);
        when(pagedResourcesAssembler.toModel(orderPage,orderConverter))
                .thenReturn(orderDtoPagedModel);
        mockMvc.perform(get("/orders").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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
