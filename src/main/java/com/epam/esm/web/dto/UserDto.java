package com.epam.esm.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@ToString
public class UserDto extends RepresentationModel<UserDto> {
    private Long id;
    private String name;

    private List<OrderDto> orders = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
