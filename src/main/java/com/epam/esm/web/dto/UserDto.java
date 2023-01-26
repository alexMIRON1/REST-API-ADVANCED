package com.epam.esm.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@ToString
public class UserDto extends RepresentationModel<UserDto> {
    private Long id;
    private String name;

    private Set<OrderDto> orders = new HashSet<>();

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
