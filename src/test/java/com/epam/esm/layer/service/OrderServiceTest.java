package com.epam.esm.layer.service;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.NoSuchEntityException;
import com.epam.esm.service.exception.WrongDataException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @MockBean
    private OrderRepository orderRepository;
    @Autowired
    private Order order;
    @Test
    void getByIdTest(){
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        Order orderTest = orderService.getById(order.getId());
        assertEquals(orderTest.getId(),order.getId());
        assertEquals(orderTest.getPrice(),order.getPrice());
        assertEquals(orderTest.getPurchaseTime(),order.getPurchaseTime());
        assertEquals(orderTest.getUser(),order.getUser());
        assertEquals(orderTest.getGiftCertificate(),order.getGiftCertificate());
    }
    @Test
    void getByIdTestNull(){
        assertThrows(WrongDataException.class,()->orderService.getById(null));
    }
    @Test
    void getByIdTestNoSuchEntity(){
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        assertThrows(NoSuchEntityException.class,()->orderService.getById(5L));
    }
    @Test
    void getAllTest(){
        int page = 1;
        int size = 10;
        when(orderRepository.findAll(PageRequest.of(page,size))).thenReturn(new PageImpl<>(List.of(order)));
        Page<Order> orderPage = orderService.getAll(page,size);
        Order orderTest = orderPage.stream().toList().stream().findFirst().orElseThrow();
        assertEquals(orderTest.getId(),order.getId());
        assertEquals(orderTest.getPrice(),order.getPrice());
        assertEquals(orderTest.getPurchaseTime(),order.getPurchaseTime());
        assertEquals(orderTest.getUser(),order.getUser());
        assertEquals(orderTest.getGiftCertificate(),order.getGiftCertificate());
    }
    @Test
    void getAllOrdersByUserIdTest(){
        User user = new User(1L, "Oleksandr Myronenko",new ArrayList<>());
        order.setUser(user);
        when(orderRepository.getAllByUserId(user.getId())).thenReturn(List.of(order));
        Order orderTest = orderService.getAllOrdersByUserId(user.getId())
                .stream().findFirst().orElseThrow();
        assertEquals(orderTest.getId(),order.getId());
        assertEquals(orderTest.getPrice(),order.getPrice());
        assertEquals(orderTest.getPurchaseTime(),order.getPurchaseTime());
        assertEquals(orderTest.getUser(),order.getUser());
        assertEquals(orderTest.getGiftCertificate(),order.getGiftCertificate());
    }
    @Test
    void getAllOrdersByUserIdTestNull(){
        assertThrows(WrongDataException.class,()->orderService.getAllOrdersByUserId(null));
    }
    @Test
    void getAllOrdersByUserIdTestNoSuchEntity(){
        User user = new User(1L, "Oleksandr Myronenko",new ArrayList<>());
        order.setUser(user);
        when(orderRepository.getAllByUserId(user.getId())).thenReturn(List.of(order));
        assertThrows(NoSuchEntityException.class,()->orderService.getAllOrdersByUserId(5L));
    }

}
