package com.epam.esm.web.converter.impl;

import com.epam.esm.model.entity.Role;
import com.epam.esm.web.converter.Converter;
import com.epam.esm.web.dto.RoleDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoleConverter implements Converter<Role, RoleDto> {
    private final ModelMapper modelMapper;
    @Override
    public @NonNull RoleDto toModel(@NonNull Role entity) {
        RoleDto roleDto = modelMapper.map(entity,RoleDto.class);
        log.info("role entity " + entity + " was successfully converted to model " + roleDto);
        return roleDto;
    }

    @Override
    public Role toEntity(RoleDto dto) {
        if (dto != null) {
            Role role = modelMapper.map(dto, Role.class);
            log.info("role model was successfully converted to entity");
            return role;
        }
        log.debug("role model was not converted to entity -> model is null");
        return null;
    }
}
