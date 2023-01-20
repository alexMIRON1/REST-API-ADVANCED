package com.epam.esm.layer.web.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.web.controller.TagController;
import com.epam.esm.web.dto.GiftCertificateDto;
import com.epam.esm.web.dto.TagDto;
import com.epam.esm.web.converter.Converter;
import com.epam.esm.web.filter.JwtAuthFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.hateoas.PagedModel.PageMetadata;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(TagController.class)
@ComponentScan("com.epam.esm.layer")
class TagControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private JacksonTester<TagDto> jacksonTester;
    private MockMvc mockMvc;
    @MockBean
    private TagService tagService;
    @MockBean
    private JwtAuthFilter jwtAuthFilter;
    @MockBean
    private Converter<Tag,TagDto> tagConverter;
    @MockBean
    private GiftCertificateService giftCertificateService;
    @MockBean
    private Converter<GiftCertificate, GiftCertificateDto> certificateConverter;
    @MockBean
    private PagedResourcesAssembler<Tag> pagedResourcesAssembler;
    @Autowired
    private Tag tag;
    @Autowired
    private TagDto tagDto;
    @Autowired
    private GiftCertificate giftCertificate;
    @Autowired
    private GiftCertificateDto giftCertificateDto;
    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        JacksonTester.initFields(this,new ObjectMapper());
    }
    @Test
    @SneakyThrows
    void getByIdTest(){
        when(tagService.getById(tag.getId())).thenReturn(tag);
        when(tagConverter.toModel(tag)).thenReturn(tagDto);
        mockMvc.perform(get("/tags/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name",is(tagDto.getName())));
    }
    @Test
    @SneakyThrows
    void getAllTest(){
        PageMetadata metadata =  new PageMetadata(10, 1,200);
        int page = 1;
        int size = 10;
        Page<Tag> tagPage = new PageImpl<>(List.of(tag));
        when(tagService.getAll(page,size)).thenReturn(tagPage);
        PagedModel<TagDto> tagDtoPagedModel =PagedModel.of(List.of(tagDto),
                metadata);
        when(pagedResourcesAssembler.toModel(tagPage,tagConverter))
                .thenReturn(tagDtoPagedModel);
        mockMvc.perform(get("/tags").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @SneakyThrows
    void createTest(){
        doNothing().when(tagService).insert(tag);
        when(tagConverter.toEntity(tagDto)).thenReturn(tag);
        mockMvc.perform(post("/tags").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTester.write(tagDto).getJson())).andExpect(status().isCreated());
    }
    @Test
    @SneakyThrows
    void addTagToCertificateTest(){
        when(giftCertificateService.getById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(certificateConverter.toModel(giftCertificate)).thenReturn(giftCertificateDto);
        when(tagService.getById(tag.getId())).thenReturn(tag);
        when(tagConverter.toModel(tag)).thenReturn(tagDto);

        when(certificateConverter.toEntity(giftCertificateDto)).thenReturn(giftCertificate);
        when(tagConverter.toEntity(tagDto)).thenReturn(tag);
        doNothing().when(giftCertificateService).addTag(giftCertificate,tag);
        mockMvc.perform(post("/tags/certificate/1").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTester.write(tagDto).getJson())).andExpect(status().isOk());
    }
    @Test
    @SneakyThrows
    void removeTagFromCertificateTest(){
        tagDto.setCertificates(null);
        tag.setCertificates(null);
        when(giftCertificateService.getById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(certificateConverter.toModel(giftCertificate)).thenReturn(giftCertificateDto);
        when(tagService.getById(tag.getId())).thenReturn(tag);
        when(tagConverter.toModel(tag)).thenReturn(tagDto);

        when(certificateConverter.toEntity(giftCertificateDto)).thenReturn(giftCertificate);
        when(tagConverter.toEntity(tagDto)).thenReturn(tag);
        doNothing().when(giftCertificateService).removeTag(giftCertificate,tag.getId());
        mockMvc.perform(delete("/tags/certificate/1").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTester.write(tagDto).getJson())).andExpect(status().isOk());
    }
    @Test
    @SneakyThrows
    void removeTest(){
        when(tagService.getById(tag.getId())).thenReturn(tag);
        when(tagConverter.toModel(tag)).thenReturn(tagDto);
        when(tagConverter.toEntity(tagDto)).thenReturn(tag);
        doNothing().when(tagService).remove(tag);
        mockMvc.perform(delete("/tags/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @SneakyThrows
    void getCertificatesWithTagsByNameTest(){
        when(certificateConverter.toModel(giftCertificate)).thenReturn(giftCertificateDto);
        when(tagConverter.toModel(tag)).thenReturn(tagDto);
        when(tagService.getCertificatesWithTagsByName(tag.getName())).thenReturn(getMap());
        mockMvc.perform(get("/tags/certificates").param("name",tag.getName())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    @Test
    @SneakyThrows
    void getTheMostWidelyUsedTagOfUserWithTheHighestCostOfOrdersTest(){
        when(tagService.getTheMostWidelyUsedTagOfUserWithTheHighestCostOfOrders()).thenReturn(tag);
        when(tagConverter.toModel(tag)).thenReturn(tagDto);
        mockMvc.perform(get("/tags/popular").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    private Map<List<GiftCertificate>,List<Tag>> getMap(){
        Map<List<GiftCertificate>,List<Tag>> map = new HashMap<>();
        map.put(List.of(giftCertificate),List.of(tag));
        return map;
    }
}
