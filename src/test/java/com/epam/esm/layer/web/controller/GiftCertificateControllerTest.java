package com.epam.esm.layer.web.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.web.controller.GiftCertificateController;
import com.epam.esm.web.dto.GiftCertificateDto;
import com.epam.esm.web.converter.Converter;
import com.epam.esm.web.filter.JwtAuthFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import static org.hamcrest.CoreMatchers.is;
import org.springframework.hateoas.PagedModel.PageMetadata;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebMvcTest(GiftCertificateController.class)
@ComponentScan("com.epam.esm.layer")
class GiftCertificateControllerTest {
    @Autowired
    private  WebApplicationContext webApplicationContext;
    private JacksonTester<GiftCertificateDto> jacksonTester;
    private MockMvc mockMvc;
    @MockBean
    private GiftCertificateService giftCertificateService;
    @MockBean
    private Converter<GiftCertificate,GiftCertificateDto> certificateConverter;
    @Autowired
    private GiftCertificate giftCertificate;
    @Autowired
    private GiftCertificateDto giftCertificateDto;
    @MockBean
    private PagedResourcesAssembler<GiftCertificate> pagedResourcesAssembler;
    @MockBean
    private JwtAuthFilter jwtAuthFilter;


    @BeforeEach
     public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        JacksonTester.initFields(this,new ObjectMapper());

    }
    @Test
    @SneakyThrows
    void getAllTest(){
        PageMetadata metadata = new PageMetadata(10, 1,200);
        int page = 1;
        int size = 10;
        Page<GiftCertificate> giftCertificatePage = new PageImpl<>(List.of(giftCertificate));
        when(giftCertificateService.getAll(page,size)).thenReturn(giftCertificatePage);
        PagedModel<GiftCertificateDto> giftCertificateDtoPagedModel =PagedModel.of(List.of(giftCertificateDto),
                metadata);
        when(pagedResourcesAssembler.toModel(giftCertificatePage,certificateConverter))
                .thenReturn(giftCertificateDtoPagedModel);
        mockMvc.perform(get("/certificates").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @SneakyThrows
     void getByIdTest(){
        when(giftCertificateService.getById(giftCertificate.getId())).thenReturn(giftCertificate);
        when(certificateConverter.toModel(giftCertificate)).thenReturn(giftCertificateDto);
        mockMvc.perform(get("/certificates/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name",is(giftCertificateDto.getName())));
    }
    @Test
    @SneakyThrows
    void createTest(){
        doNothing().when(giftCertificateService).insert(giftCertificate);
        when(certificateConverter.toEntity(giftCertificateDto)).thenReturn(giftCertificate);
        mockMvc.perform(post("/certificates").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTester.write(giftCertificateDto).getJson())).andExpect(status().isCreated());
    }
    @Test
    @SneakyThrows
    void removeTest(){
        doNothing().when(giftCertificateService).remove(giftCertificate);
        when(certificateConverter.toEntity(giftCertificateDto)).thenReturn(giftCertificate);
        mockMvc.perform(delete("/certificates").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTester.write(giftCertificateDto).getJson())).andExpect(status().isOk());
    }
    @Test
    @SneakyThrows
    void updateTest(){
        doNothing().when(giftCertificateService).update(giftCertificate);
        when(certificateConverter.toEntity(giftCertificateDto)).thenReturn(giftCertificate);
        mockMvc.perform(put("/certificates").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTester.write(giftCertificateDto).getJson())).andExpect(status().isOk());
    }
    @Test
    @SneakyThrows
    void getGiftCertificatesByDescriptionTest(){
        GiftCertificate secondGiftCertificate = new GiftCertificate(2L,"New Day","Congratulations",
                BigDecimal.valueOf(4000.10),10,Instant.now(),Instant.now(),new ArrayList<>());
        GiftCertificateDto secondGiftCertificateDto = new GiftCertificateDto(2L,"New Day","Congratulations",
                BigDecimal.valueOf(4000.10),10,Instant.now(),Instant.now(),new ArrayList<>());
        when(giftCertificateService.getCertificatesWithTagsByPartOfDescription("Congratulations"))
                .thenReturn(List.of(giftCertificate,secondGiftCertificate));
        when(certificateConverter.toModel(giftCertificate)).thenReturn(giftCertificateDto);
        when(certificateConverter.toModel(secondGiftCertificate)).thenReturn(secondGiftCertificateDto);
        mockMvc.perform(get("/certificates/tags/Congratulations").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].name",is("New Year")))
                .andExpect(jsonPath("$[1].name",is("New Day")));
    }
    @Test
    @SneakyThrows
    void getGiftCertificatesWithTagsSortedByCreateDateAscTest(){
        PageMetadata metadata = new PageMetadata(10, 1,200);
        int page = 1;
        int size = 10;
        List<GiftCertificate> giftCertificatesTest = new ArrayList<>();
        giftCertificatesTest.add(giftCertificate);
        giftCertificatesTest.add(new GiftCertificate(2L,"Aaa","bbb",
                BigDecimal.valueOf(1800.00),14,Instant.now(),Instant.now(),new ArrayList<>()));
        Page<GiftCertificate> giftCertificatePage = new PageImpl<>(giftCertificatesTest
                .stream().sorted(Comparator.comparing(GiftCertificate::getCreateDate)).toList());
        List<GiftCertificateDto> giftCertificateDtoList = new ArrayList<>();
        giftCertificateDtoList.add(giftCertificateDto);
        giftCertificateDtoList.add(new GiftCertificateDto(2L,"Aaa","bbb",
                BigDecimal.valueOf(1800.00),14,Instant.now(),Instant.now(),new ArrayList<>()));
        when(giftCertificateService.getCertificatesWithTagsSortByCreateDateASC(page,size)).thenReturn(giftCertificatePage);
        PagedModel<GiftCertificateDto> giftCertificateDtoPagedModel =PagedModel.of(giftCertificateDtoList
                        .stream().sorted(Comparator.comparing(GiftCertificateDto::getCreateDate)).toList(),
                metadata);
        when(pagedResourcesAssembler.toModel(giftCertificatePage,certificateConverter))
                .thenReturn(giftCertificateDtoPagedModel);
        mockMvc.perform(get("/certificates/tags/sortedASC").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

