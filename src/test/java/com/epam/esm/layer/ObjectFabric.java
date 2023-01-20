package com.epam.esm.layer;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.web.dto.GiftCertificateDto;
import com.epam.esm.web.dto.OrderDto;
import com.epam.esm.web.dto.RoleDto;
import com.epam.esm.web.dto.TagDto;
import com.epam.esm.web.dto.UserDto;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * This class {@code  ObjectFabric} creates instances of objects.
 * @author Oleksandr Myronenko
 */
@Component
public class ObjectFabric {
    @Bean
    public GiftCertificate createGiftCertificate(){
        return  new GiftCertificate(1L,"New Year",
                "Congratulations with new year",
                BigDecimal.valueOf(500.50),14, Instant.now(),Instant.now(),new ArrayList<>());
    }
    @Bean
    public GiftCertificateDto createGiftCertificateDto(){
        return new GiftCertificateDto(1L,"New Year",
                "Congratulations with new year",
                BigDecimal.valueOf(500.50),14, Instant.now(),Instant.now(),new ArrayList<>());
    }
    @Bean
    public Tag createTag(GiftCertificate giftCertificate){
        return new Tag(1L,"Apple shop", List.of(giftCertificate));
    }
    @Bean
    public TagDto createTagDto(GiftCertificateDto giftCertificateDto){
        return new TagDto(1L,"Apple shop",List.of(giftCertificateDto));
    }
    @Bean
    public User createUser(Order order){
        return new User(1L,"Oleksandr Myronenko", "password",
                new Role(1,"User",new ArrayList<>()),List.of(order));
    }
    @Bean
    public UserDto createUserDto(OrderDto orderDto){
        return new UserDto(1L,"Oleksandr Myronenko",
                "password", new RoleDto(1,"User",new ArrayList<>()),List.of(orderDto));
    }
    @Bean
    public Order createOrder(){
        return new Order(1L,BigDecimal.valueOf(500.50),Instant.now()
                ,new User(1L,"Oleksandr Myronenko",
                "password", new Role(1,"User",new ArrayList<>()),new ArrayList<>()),
                new GiftCertificate(1L,"New Year",
                        "Congratulations with new year",
                        BigDecimal.valueOf(500.50),14, Instant.now(),Instant.now(),new ArrayList<>()));
    }
    @Bean
    public OrderDto createOrderDto(){
        return new OrderDto(1L,BigDecimal.valueOf(500.50),Instant.now(),
                new UserDto(1L,"Oleksandr Myronenko",
                        "password", new RoleDto(1,"User",new ArrayList<>()),
                        new ArrayList<>()),
                new GiftCertificateDto(1L,"New Year",
                        "Congratulations with new year",
                        BigDecimal.valueOf(500.50),14, Instant.now(),Instant.now(),new ArrayList<>()));
    }
}
