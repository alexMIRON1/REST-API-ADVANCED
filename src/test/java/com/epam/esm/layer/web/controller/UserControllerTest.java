package com.epam.esm.layer.web.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.web.controller.UserController;
import com.epam.esm.web.dto.GiftCertificateDto;
import com.epam.esm.web.dto.OrderDto;
import com.epam.esm.web.dto.UserDto;
import com.epam.esm.web.converter.Converter;
import com.epam.esm.web.filter.JwtAuthFilter;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ComponentScan("com.epam.esm.layer")
class UserControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private JacksonTester<UserDto> jacksonTesterUser;
    private JacksonTester<GiftCertificateDto> jacksonTesterCertificate;
    private JacksonTester<OrderDto> jacksonTesterOrder;
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private JwtAuthFilter jwtAuthFilter;
    @MockBean
    private GiftCertificateService giftCertificateService;
    @MockBean
    private OrderService orderService;
    @MockBean
    private Converter<User,UserDto> userConverter;
    @MockBean
    private Converter<GiftCertificate, GiftCertificateDto> certificateConverter;
    @MockBean
    private Converter<Order, OrderDto> orderConverter;
    @MockBean
    private PagedResourcesAssembler<User> pagedResourcesAssembler;
    @Autowired
    private User user;
    @Autowired
    private UserDto userDto;
    @Autowired
    private GiftCertificate giftCertificate;
    @Autowired
    private GiftCertificateDto giftCertificateDto;
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
        when(userService.getById(user.getId())).thenReturn(user);
        when(userConverter.toModel(user)).thenReturn(userDto);
        mockMvc.perform(get("/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name",is(userDto.getName())));
    }
    @Test
    @SneakyThrows
    void getAllTest(){
        PageMetadata metadata =  new PageMetadata(10, 1,200);
        int page = 1;
        int size = 10;
        Page<User> userPage = new PageImpl<>(List.of(user));
        when(userService.getAll(page,size)).thenReturn(userPage);
        PagedModel<UserDto> userDtoPagedModel =PagedModel.of(List.of(userDto),
                metadata);
        when(pagedResourcesAssembler.toModel(userPage,userConverter))
                .thenReturn(userDtoPagedModel);
        mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @SneakyThrows
    void createTest(){
        when(userConverter.toEntity(userDto)).thenReturn(user);
        doNothing().when(userService).insert(user);
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTesterUser.write(userDto).getJson())).andExpect(status().isCreated());
    }
    @Test
    @SneakyThrows
    void makerOrderTest(){
        user.setOrders(null);
        userDto.setOrders(null);
        when(giftCertificateService.getById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(certificateConverter.toModel(giftCertificate)).thenReturn(giftCertificateDto);
        when(userService.getById(user.getId())).thenReturn(user);
        when(userConverter.toModel(user)).thenReturn(userDto);
        when(userConverter.toEntity(userDto)).thenReturn(user);
        when(certificateConverter.toEntity(giftCertificateDto)).thenReturn(giftCertificate);
        when(userService.makeOrder(user,giftCertificate)).thenReturn(order);
        mockMvc.perform(post("/users/1/order").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTesterCertificate.write(giftCertificateDto).getJson())).andExpect(status().isOk());
    }
    @Test
    @SneakyThrows
    void removeTest(){
        when(userConverter.toEntity(userDto)).thenReturn(user);
        doNothing().when(userService).remove(user);
        mockMvc.perform(delete("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonTesterUser.write(userDto).getJson()))
                .andExpect(status().isOk());
    }
    @Test
    @SneakyThrows
    void removeOrderTest(){
        when(userService.getById(user.getId())).thenReturn(user);
        when(userConverter.toModel(user)).thenReturn(userDto);
        when(orderService.getById(orderDto.getId())).thenReturn(order);
        when(orderConverter.toModel(order)).thenReturn(orderDto);
        when(userConverter.toEntity(userDto)).thenReturn(user);
        doNothing().when(userService).removeOrder(user, order.getId());
        mockMvc.perform(delete("/users/1/order").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTesterOrder.write(orderDto).getJson()))
                .andExpect(status().isOk());
    }
}
