package com.epam.esm.layer.model;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
@DataJpaTest
@ComponentScan("com.epam.esm.layer")
 class OrderRepositoryTest{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private Order order;
    @Autowired
    private GiftCertificate giftCertificate;
    @Autowired
    private User user;
    @Test
    void getAllByUserIdTest(){
        testEntityManager.merge(giftCertificate);
        user.setOrders(new ArrayList<>());
        testEntityManager.merge(new Role(1,"User", new ArrayList<>()));
        testEntityManager.merge(user);
        user.setOrders(List.of(order));
        testEntityManager.merge(order);
        Order orderTest = orderRepository.getAllByUserId(order.getUser().getId()).stream().findFirst().orElseThrow();
        assertEquals(orderTest.getId(),order.getId());
        assertEquals(orderTest.getPrice(),order.getPrice());
        assertEquals(orderTest.getGiftCertificate(),order.getGiftCertificate());
        assertEquals(orderTest.getUser(),order.getUser());
        assertEquals(orderTest.getPurchaseTime(),order.getPurchaseTime());


    }
}
