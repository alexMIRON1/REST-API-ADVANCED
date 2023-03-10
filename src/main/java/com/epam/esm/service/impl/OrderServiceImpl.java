package com.epam.esm.service.impl;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.NoSuchEntityException;
import com.epam.esm.service.exception.UnsupportedOperationException;
import com.epam.esm.service.exception.WrongDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Page<Order> getAll(int page, int size){
        return orderRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public void insert(Order model) {
        throw new UnsupportedOperationException("This method does not support");
    }

    @Override
    public void remove(Order item) {
        throw new UnsupportedOperationException("This method does not support");
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
