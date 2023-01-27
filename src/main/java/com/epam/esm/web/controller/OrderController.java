package com.epam.esm.web.controller;

import com.epam.esm.model.entity.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.web.dto.OrderDto;
import com.epam.esm.web.mapper.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class {@code OrderController} represents endpoint of the API which allows you to do operations on orders.
 * {@code OrderController} is accessed to send request by /orders.
 * @author Oleksandr Myronenko
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final Converter<Order, OrderDto> orderConverter;
    private final PagedResourcesAssembler<Order> pagedResourcesAssembler;

    /**
     * This method is used to get order by id.
     * @param orderId order's id
     * @return order dto
     */
    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getById(@PathVariable("orderId") Long orderId){
        return orderConverter.toModel(orderService.getById(orderId));
    }

    /**
     * This method is used to get all orders on page.
     * @param page number of page
     * @return page of orders
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<OrderDto> getAll(@RequestParam(defaultValue = "0") int page){
        Page<Order> orders = orderService.getAll(page,10);
        return pagedResourcesAssembler.toModel(orders,orderConverter);
    }

    /**
     * This method is used to get all orders by user's id.
     * @param userId user's id
     * @return list of orders dto
     */
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getOrdersByUserId(@PathVariable("userId") Long userId){
        return orderService.getAllOrdersByUserId(userId).stream().map(orderConverter::toModel).toList();
    }
}
