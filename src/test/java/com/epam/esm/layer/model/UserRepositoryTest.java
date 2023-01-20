package com.epam.esm.layer.model;

import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
@DataJpaTest
@ComponentScan("com.epam.esm.layer")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private User user;
    @Test
    void findUserByName(){
        String name = user.getName();
        testEntityManager.persistAndFlush(new Role(1,"User",new ArrayList<>()));
        testEntityManager.persistAndFlush(user);
        User testUser = userRepository.findUserByName(name).orElseThrow();
        assertEquals(user.getId(),testUser.getId());
        assertEquals(user.getName(),testUser.getName());
        assertEquals(user.getPassword(),testUser.getPassword());
        assertEquals(user.getRole().getName(),testUser.getRole().getName());
        assertEquals(user.getOrders(),testUser.getOrders());
    }

}
