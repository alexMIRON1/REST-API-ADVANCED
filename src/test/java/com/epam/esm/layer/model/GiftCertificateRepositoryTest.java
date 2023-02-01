package com.epam.esm.layer.model;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.GiftCertificateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@ComponentScan("com.epam.esm.layer")
 class GiftCertificateRepositoryTest {
    @Autowired
    private GiftCertificateRepository giftCertificateRepository;
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private GiftCertificate giftCertificate;
    @Test
    void getGiftCertificateByPartOfDescriptionTest(){
        testEntityManager.persistAndFlush(giftCertificate);
        String description = "Congratulations";
        List<GiftCertificate> giftCertificateTest = giftCertificateRepository
                .getGiftCertificateByPartOfDescription(description);
        assertThat(giftCertificateTest).extracting(GiftCertificate::getDescription)
                .anySatisfy(string->assertThat(string).contains(description));
    }
    @Test
    void getGiftCertificatesSortedByCreateDateASCTest(){
        testEntityManager.persistAndFlush(giftCertificate);
        GiftCertificate giftCertificateTest = new GiftCertificate(2L,"aaaa","bbb",
                BigDecimal.valueOf(4000.13),14, Instant.now(),Instant.now(),new ArrayList<>());
        testEntityManager.persistAndFlush(giftCertificateTest);
        List<GiftCertificate> giftCertificates = giftCertificateRepository
                .getGiftCertificatesSortedByCreateDateASC(PageRequest.of(1,10)).stream().toList();
        List<GiftCertificate> giftCertificatesTest = new ArrayList<>();
        giftCertificatesTest.add(giftCertificate);
        giftCertificatesTest.add(giftCertificateTest);
        giftCertificatesTest.sort(Comparator.comparing(GiftCertificate::getCreateDate));
        for (int i = 0; i < giftCertificates.size(); i++) {
            assertEquals(giftCertificates.get(i).getId(), giftCertificatesTest.get(i).getId());
            assertEquals(giftCertificates.get(i).getName(), giftCertificatesTest.get(i).getName());
            assertEquals(giftCertificates.get(i).getDescription(), giftCertificatesTest.get(i).getDescription());
            assertEquals(giftCertificates.get(i).getPrice(), giftCertificatesTest.get(i).getPrice());
            assertEquals(giftCertificates.get(i).getCreateDate(), giftCertificatesTest.get(i).getCreateDate());
            assertEquals(giftCertificates.get(i).getLastUpdateDate(), giftCertificatesTest.get(i).getLastUpdateDate());
        }
    }
    @Test
    void getGiftCertificateByTagNameTest(){
        testEntityManager.persistAndFlush(giftCertificate);
        String name = "Equipment shop";
        Tag tag = new Tag(1L,name,new ArrayList<>());
        GiftCertificate giftCertificateTest = new GiftCertificate(2L,"aaaa","bbb",
                BigDecimal.valueOf(4000.13),14, Instant.now(),Instant.now(),new ArrayList<>());
        giftCertificateTest.addTag(tag);
        testEntityManager.persistAndFlush(giftCertificateTest);
        List<GiftCertificate> giftCertificates = giftCertificateRepository.getGiftCertificateByTagName(name);
        List<GiftCertificate> temporal = new ArrayList<>();
        temporal.add(giftCertificate);
        temporal.add(giftCertificateTest);
        List<GiftCertificate> giftCertificatesTest = new ArrayList<>();
        for (GiftCertificate g : temporal) {
            for (Tag t: g.getTags()) {
                if(Objects.equals(t.getName(), name)){
                    giftCertificatesTest.add(g);
                }
            }
        }
        for (int i = 0; i < giftCertificates.size(); i++) {
            assertEquals(giftCertificates.get(i).getId(), giftCertificatesTest.get(i).getId());
            assertEquals(giftCertificates.get(i).getName(), giftCertificatesTest.get(i).getName());
            assertEquals(giftCertificates.get(i).getDescription(), giftCertificatesTest.get(i).getDescription());
            assertEquals(giftCertificates.get(i).getPrice(), giftCertificatesTest.get(i).getPrice());
            assertEquals(giftCertificates.get(i).getCreateDate(), giftCertificatesTest.get(i).getCreateDate());
            assertEquals(giftCertificates.get(i).getLastUpdateDate(), giftCertificatesTest.get(i).getLastUpdateDate());
        }
    }
}
