package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.NoSuchEntityException;
import com.epam.esm.service.exception.WrongDataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;


    @Override
    public User getById(Long id) {
        if(id==null){
            log.error("id is null");
            throw new WrongDataException("Id was null");
        }
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            log.error("user does not exist with id  " + id);
            throw new NoSuchEntityException("User with id " + id + " does not exist");
        }
        log.info("successfully got user " + userOptional + " by user's id " + id);
        return userOptional.get();
    }

    @Override
    public Page<User> getAll(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public void insert(User model) {
        if (model==null){
            log.error("user is null");
            throw new WrongDataException("User was null");
        }
        userRepository.save(model);
        log.info("successfully created user -> " + model);
    }

    @Override
    public void remove(User item) {
        Optional<User> userOptional = userRepository.findById(item.getId());
        if(userOptional.isEmpty()){
            log.error("user does not exist by id " + item.getId());
            throw new NoSuchEntityException("User does not exist with id " + item.getId());
        }
        userRepository.delete(userOptional.get());
        log.info("successfully deleted user -> " + userOptional);
    }

    @Override
    @Transactional
    public void update(User entity) {
        User user = userRepository.findById(entity.getId())
                .orElseThrow(()->new NoSuchEntityException("User with id " + entity.getId()
                        + " does not exist"));
        user.setOrders(entity.getOrders());
        log.info("successfully set orders " + entity.getOrders() + " to user -> " + user);

    }
    @Override
    @Transactional
    public void makeOrder(User user, GiftCertificate giftCertificate) {
        Order order = new Order(giftCertificate.getPrice(), Instant.now(),user,giftCertificate);
        orderRepository.save(order);
        log.info("successfully saved order " + order);
        order.setUser(user);
        update(user);
    }
    @Override
    public void removedOrder(User user, Long orderId) {
        orderRepository.deleteById(orderId);
        log.info("successfully removed order by order's id " + orderId + " from user " + user.getOrders());
    }
}
