package com.epam.esm.layer.web.converter;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.web.converter.Converter;
import com.epam.esm.web.dto.GiftCertificateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ComponentScan("com.epam.esm.layer")
 class GiftCertificateConverterTest {
    @Autowired
    private GiftCertificate giftCertificate;
    @Autowired
    private GiftCertificateDto giftCertificateDto;
    @Autowired
    private Converter<GiftCertificate,GiftCertificateDto> certificateConverter;
    @Test
    void toModelTest(){
        GiftCertificateDto testGiftCertificateDto = certificateConverter.toModel(giftCertificate);
        assertEquals(testGiftCertificateDto.getId(),giftCertificate.getId());
        assertEquals(testGiftCertificateDto.getName(),giftCertificate.getName());
        assertEquals(testGiftCertificateDto.getDescription(),giftCertificate.getDescription());
        assertEquals(testGiftCertificateDto.getCreateDate(),giftCertificate.getCreateDate());
        assertEquals(testGiftCertificateDto.getDuration(),giftCertificate.getDuration());
        assertEquals(testGiftCertificateDto.getLastUpdateDate(),giftCertificate.getLastUpdateDate());
        assertEquals(testGiftCertificateDto.getPrice(),giftCertificate.getPrice());
    }
    @Test
    void toEntityTest(){
        GiftCertificate testGiftCertificate = certificateConverter.toEntity(giftCertificateDto);
        assertEquals(testGiftCertificate.getId(),giftCertificateDto.getId());
        assertEquals(testGiftCertificate.getName(),giftCertificateDto.getName());
        assertEquals(testGiftCertificate.getPrice(),giftCertificateDto.getPrice());
        assertEquals(testGiftCertificate.getDuration(),giftCertificateDto.getDuration());
        assertEquals(testGiftCertificate.getCreateDate(),giftCertificateDto.getCreateDate());
        assertEquals(testGiftCertificate.getLastUpdateDate(),giftCertificateDto.getLastUpdateDate());
        assertEquals(testGiftCertificate.getDescription(),giftCertificateDto.getDescription());
    }
}
