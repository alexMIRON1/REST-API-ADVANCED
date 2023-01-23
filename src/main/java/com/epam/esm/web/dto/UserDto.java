package com.epam.esm.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Getter
@Setter
@ToString
public class UserDto {
    private Long id;
    private String name;

    private Set<OrderDto> orders = new HashSet<>();

    public void addOrder(OrderDto orderDto){
        this.orders.add(orderDto);
        orderDto.setUser(this);
    }
    public void removeOrder(Long id){
        OrderDto order = this.orders.stream().filter(t-> Objects.equals(t.getId(),id)).findFirst().orElse(null);
        if(order!=null){
            this.orders.remove(order);
            order.setUser(null);
        }
    }
}
