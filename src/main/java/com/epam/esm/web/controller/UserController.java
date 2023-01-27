package com.epam.esm.web.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.web.dto.GiftCertificateDto;
import com.epam.esm.web.dto.OrderDto;
import com.epam.esm.web.dto.UserDto;
import com.epam.esm.web.mapper.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class {@code UserController} represents endpoint of API which allows you to do operations on users.
 * {@code UserController} is accessed to send request by /users.
 * @author Oleksandr Myronenko
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final GiftCertificateService giftCertificateService;
    private final OrderService orderService;
    private final Converter<User, UserDto> userConverter;
    private final Converter<GiftCertificate, GiftCertificateDto> certificateConverter;
    private final Converter<Order,OrderDto> orderConverter;
    private final PagedResourcesAssembler<User> pagedResourcesAssembler;

    /**
     * This method is used to get user by user's id.
     * @param userId user's id
     * @return user dto
     */
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getById(@PathVariable("userId") Long userId){
        return userConverter.toModel(userService.getById(userId));
    }

    /**
     * This method is used to get all users.
     * @param page number of page
     * @return page of users dto
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<UserDto> getAll(@RequestParam(defaultValue = "0") int page){
        Page<User> users = userService.getAll(page,10);
        return pagedResourcesAssembler.toModel(users,userConverter);
    }

    /**
     * This method is used to create user.
     * @param userDto user dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserDto userDto){
        userService.insert(userConverter.toEntity(userDto));
    }

    /**
     * This method is used to make order - buy certificate.
     * @param userId user's id
     * @param giftCertificateDto gift certificate dto
     */
    @PostMapping("/{userId}/order")
    @ResponseStatus(HttpStatus.OK)
    public void makeOrder(@PathVariable("userId") Long userId,
                          @RequestBody GiftCertificateDto giftCertificateDto){
        GiftCertificateDto certificateDto = certificateConverter
                .toModel(giftCertificateService.getById(giftCertificateDto.getId()));
        UserDto user = userConverter.toModel(userService.getById(userId));
        userService.makeOrder(userConverter.toEntity(user),certificateConverter.toEntity(certificateDto));
    }

    /**
     * This method is used to delete user.
     * @param userDto user dto
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestBody UserDto userDto){
        userService.remove(userConverter.toEntity(userDto));
    }

    /**
     * This method is used to delete order from user.
     * @param userId user's id
     * @param orderDto order dto
     */
    @DeleteMapping("/{userId}/order")
    @ResponseStatus(HttpStatus.OK)
    public void removeOrder(@PathVariable("userId") Long userId,
                            @RequestBody OrderDto orderDto){
        UserDto userDto = userConverter.toModel(userService.getById(userId));
        OrderDto order = orderConverter.toModel(orderService.getById(orderDto.getId()));
        userService.removedOrder(userConverter.toEntity(userDto), order.getId());
    }
}
