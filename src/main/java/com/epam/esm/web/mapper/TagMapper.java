package com.epam.esm.web.mapper;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.web.dto.TagDto;
import org.mapstruct.Mapper;

/**
 * Interface {@code TagMapper} describes converting from dto {@link TagDto} to entity {@link Tag} and vice versa.
 */
@Mapper(componentModel = "spring")
public interface TagMapper {
    /**
     * Method for converting entity tag to tag dto
     * @param tag entity tag
     * @return tag dto
     */
    TagDto toDto(Tag tag);

    /**
     * Method for converting tag dto to entity tag
     * @param tagDto tag dto
     * @return entity tag
     */
    Tag toEntity(TagDto tagDto);
}
