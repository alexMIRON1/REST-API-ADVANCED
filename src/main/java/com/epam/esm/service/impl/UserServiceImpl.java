package com.epam.esm.service.impl;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.NoSuchEntityException;
import com.epam.esm.service.exception.WrongDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


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
    public List<User> getAll() {
        return userRepository.findAll();
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
    public User getUserByHighestCostOfOrders() {
        User user = userRepository.getUserByHighestCostOfOrders();
        if(user==null){
            log.error("user is null");
            throw new NoSuchEntityException("User does not exist");
        }
        log.info("successfully get user by highest cor of all orders -> " + user);
        return user;
    }
}
