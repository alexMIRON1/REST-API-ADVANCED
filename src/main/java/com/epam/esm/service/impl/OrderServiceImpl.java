package com.epam.esm.service.impl;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.NoSuchEntityException;
import com.epam.esm.service.exception.WrongDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public Order getById(Long id) {
        if(id==null){
            log.error("id is null");
            throw new WrongDataException("Id was null");
        }
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(optionalOrder.isEmpty()){
            log.error("order does not exist -> " + optionalOrder);
            throw new NoSuchEntityException("Order with id " + id + " doesn't exist");
        }
        log.info("successfully got order " + optionalOrder.get() + " by id " + id);
        return optionalOrder.get();
    }

    @Override
    public List<Order> getAll(){
        return orderRepository.findAll();
    }

    @Override
    public void insert(Order model) {
        if(model==null){
            log.error("order is null");
            throw new WrongDataException("Order was null");
        }
        orderRepository.save(model);
        log.info("successfully created order -> " + model );
    }

    @Override
    public void remove(Order item) {
        Optional<Order> optionalOrder = orderRepository.findById(item.getId());
        if(optionalOrder.isEmpty()){
            log.error("order " + optionalOrder + " with id " + item.getId() + " does not exist" );
            throw new NoSuchEntityException("Order with id " + item.getId() + " does not exist");
        }
        orderRepository.delete(optionalOrder.get());
        log.info("successfully deleted order " + optionalOrder);
    }

    @Override
    public List<Order> getAllOrdersByUserId(Long id) {
        if(id==null){
            log.error("id is null");
            throw new WrongDataException("Id was null");
        }
        List<Order> orders = orderRepository.getAllByUserId(id);
        if(orders.isEmpty()){
            log.error("orders " + orders + " are empty");
            throw new NoSuchEntityException("List of orders does not exist");
        }
        log.info("successfully got list of orders " + orders + " by user id " + id);
        return orders;
    }
}
