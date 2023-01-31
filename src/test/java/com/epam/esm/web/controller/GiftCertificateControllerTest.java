package com.epam.esm.web.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.web.controller.GiftCertificateController;
import com.epam.esm.web.dto.GiftCertificateDto;
import com.epam.esm.web.converter.Converter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.CoreMatchers.is;

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
import java.util.List;

@WebMvcTest(GiftCertificateController.class)
@ComponentScan("com.epam.esm.web")
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

    @BeforeEach
     public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        JacksonTester.initFields(this,new ObjectMapper());
    }
    @SneakyThrows
    @Test
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
}

