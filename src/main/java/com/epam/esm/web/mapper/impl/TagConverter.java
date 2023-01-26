package com.epam.esm.web.mapper.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.web.dto.TagDto;
import com.epam.esm.web.mapper.Converter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TagConverter implements Converter<Tag, TagDto> {

    private final ModelMapper modelMapper;

    @Override
    public @NonNull TagDto toModel(@NonNull Tag entity) {
        TagDto model = modelMapper.map(entity,TagDto.class);
        log.info( model.getId() + "tag entity was successfully converted to model");
        return model;
    }

    @Override
    public Tag toEntity(TagDto dto) {
        if(dto!=null){
            Tag entity = modelMapper.map(dto,Tag.class);
            log.info(entity.getId() + "tag model was successfully converted to entity");
            return entity;
        }
        log.debug("tag model was not converted to entity -> model is null");
        return null;
    }
}
