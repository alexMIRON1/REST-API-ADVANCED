package com.epam.esm.layer.service;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.NoSuchEntityException;
import com.epam.esm.service.exception.WrongDataException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private User user;
    @Autowired
    private Order order;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private OrderRepository orderRepository;
    @Test
    void getByIdTest(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        User userTest = userService.getById(user.getId());
        assertEquals(userTest.getId(),user.getId());
        assertEquals(userTest.getName(),user.getName());
        assertEquals(userTest.getOrders(),user.getOrders());
    }
    @Test
    void getByIdTestNull(){
        assertThrows(WrongDataException.class,()->userService.getById(null));
    }
    @Test
    void getByIdTestNoSuchEntity(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        assertThrows(NoSuchEntityException.class,()->userService.getById(5L));
    }
    @Test
    void getAllTest(){
        int page = 1;
        int size = 10;
        when(userRepository.findAll(PageRequest.of(page,size))).thenReturn(new PageImpl<>(List.of(user)));
        User userTest = userService.getAll(page,size).stream().findFirst().orElseThrow();
        assertEquals(userTest.getId(),user.getId());
        assertEquals(userTest.getName(),user.getName());
        assertEquals(userTest.getOrders(),user.getOrders());
    }
    @Test
    void insertTest(){
        when(userRepository.save(user)).thenReturn(user);
        userService.insert(user);
        verify(userRepository,times(1)).save(user);
    }
    @Test
    void insertTestNull(){
        assertThrows(WrongDataException.class,()->userService.insert(null));
    }
    @Test
    void removeTest(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);
        userService.remove(user);
        verify(userRepository,times(1)).delete(user);
    }
    @Test
    void removeTestNoSuchEntity(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        User userTest = new User();
        assertThrows(NoSuchEntityException.class,()->userService.remove(userTest));
    }
    @Test
    void updateTest(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        if(user.getOrders().isEmpty()){
            user.setOrders(List.of(order));
        }
        userService.update(user);
        assertEquals(user.getOrders(),List.of(order));
    }
    @Test
    void updateTestNoSuchEntity(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        User userTest = new User();
        assertThrows(NoSuchEntityException.class,()->userService.update(userTest));
    }
    @Test
    void makeOrderTest(){
        GiftCertificate giftCertificate = new GiftCertificate(2L,"Christmas"
        ,"Marry Christmas", BigDecimal.valueOf(100.00),14, Instant.now(),Instant.now(),
                new ArrayList<>());
        Order orderTest = new Order(2L,giftCertificate.getPrice(),Instant.now(),user,giftCertificate);
        List<Order> orders = new ArrayList<>();
        orders.add(orderTest);
        when(orderRepository.save(orderTest)).thenReturn(orderTest);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        user.setOrders(orders);
        userService.makeOrder(user,giftCertificate);
        assertEquals(user.getOrders(), orders);
    }
    @Test
    void removeOrder(){
        doNothing().when(orderRepository).deleteById(order.getId());
        List<Order> orderTest = user.getOrders()
                .stream().filter(o-> !Objects.equals(o.getId(), order.getId())).toList();
        user.setOrders(orderTest);
        userService.removeOrder(user,order.getId());
        assertEquals(user.getOrders(),orderTest);
    }

}
