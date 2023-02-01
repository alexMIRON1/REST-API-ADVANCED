package com.epam.esm.layer.web.converter;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.web.converter.Converter;
import com.epam.esm.web.dto.TagDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ComponentScan("com.epam.esm.layer")
class TagConverterTest {
    @Autowired
    private Tag tag;
    @Autowired
    private TagDto tagDto;
    @Autowired
    private Converter<Tag,TagDto> tagConverter;
    @Test
    void toModelTest(){
        TagDto tagDtoTest = tagConverter.toModel(tag);
        assertEquals(tagDtoTest.getId(),tag.getId());
        assertEquals(tagDtoTest.getName(),tag.getName());
    }
    @Test
    void toEntityTest(){
        Tag tagTest = tagConverter.toEntity(tagDto);
        assertEquals(tagTest.getId(),tagDto.getId());
        assertEquals(tagTest.getName(),tagDto.getName());
    }
}
