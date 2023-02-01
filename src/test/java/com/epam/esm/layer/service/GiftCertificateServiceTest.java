package com.epam.esm.layer.service;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.NoSuchEntityException;
import com.epam.esm.service.exception.WrongDataException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
class GiftCertificateServiceTest {

    @Autowired
    private GiftCertificateService giftCertificateService;
    @MockBean
    private GiftCertificateRepository giftCertificateRepository;
    @Autowired
    private GiftCertificate giftCertificate;

    @Test
    void getByIdTest(){
        when(giftCertificateRepository.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        GiftCertificate giftCertificateTest = giftCertificateService.getById(giftCertificate.getId());
        assertEquals(giftCertificateTest.getId(),giftCertificate.getId());
        assertEquals(giftCertificateTest.getName(),giftCertificate.getName());
        assertEquals(giftCertificateTest.getDescription(),giftCertificate.getDescription());
        assertEquals(giftCertificateTest.getCreateDate(),giftCertificate.getCreateDate());
        assertEquals(giftCertificateTest.getPrice(),giftCertificate.getPrice());
        assertEquals(giftCertificateTest.getDuration(),giftCertificate.getDuration());
        assertEquals(giftCertificateTest.getLastUpdateDate(),giftCertificate.getLastUpdateDate());
        assertEquals(giftCertificateTest.getTags(),giftCertificate.getTags());
    }
    @Test
    void getByIdTestNullId(){
        assertThrows(WrongDataException.class,()->giftCertificateService.getById(null));
    }
    @Test
    void getByIdTestNoSuchEntity(){
        when(giftCertificateRepository.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        assertThrows(NoSuchEntityException.class,()->giftCertificateService.getById(5L));
    }
    @Test
    void getAllTest(){
        int page = 1;
        int size = 10;
        when(giftCertificateRepository.findAll(PageRequest.of(page,size)))
                .thenReturn(new PageImpl<>(List.of(giftCertificate)));
        Page<GiftCertificate> giftCertificatesTest = giftCertificateService.getAll(page,size);
        GiftCertificate giftCertificateTest = giftCertificatesTest.stream().findFirst().orElseThrow();
        assertEquals(giftCertificateTest.getId(),giftCertificate.getId());
        assertEquals(giftCertificateTest.getName(),giftCertificate.getName());
        assertEquals(giftCertificateTest.getDescription(),giftCertificate.getDescription());
        assertEquals(giftCertificateTest.getCreateDate(),giftCertificate.getCreateDate());
        assertEquals(giftCertificateTest.getPrice(),giftCertificate.getPrice());
        assertEquals(giftCertificateTest.getDuration(),giftCertificate.getDuration());
        assertEquals(giftCertificateTest.getLastUpdateDate(),giftCertificate.getLastUpdateDate());
        assertEquals(giftCertificateTest.getTags(),giftCertificate.getTags());
    }
    @Test
    void insertTest(){
        when(giftCertificateRepository.save(giftCertificate)).thenReturn(giftCertificate);
        giftCertificateService.insert(giftCertificate);
        verify(giftCertificateRepository,times(1)).save(giftCertificate);
    }
    @Test
    void insertTestNull(){
        assertThrows(WrongDataException.class,()->giftCertificateService.insert(null));
    }
    @Test
    void removeTest(){
        when(giftCertificateRepository.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        doNothing().when(giftCertificateRepository).delete(giftCertificate);
        giftCertificateService.remove(giftCertificate);
        verify(giftCertificateRepository,times(1)).findById(giftCertificate.getId());
        verify(giftCertificateRepository,times(1)).delete(giftCertificate);
    }
    @Test
    void removeTestNoSuchEntity(){
        when(giftCertificateRepository.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        GiftCertificate giftCertificateTest = new GiftCertificate();
        assertThrows(NoSuchEntityException.class,()->giftCertificateService.remove(giftCertificateTest));
    }
    @Test
    void updateTest(){
        when(giftCertificateRepository.save(giftCertificate)).thenReturn(giftCertificate);
        giftCertificateService.update(giftCertificate);
        verify(giftCertificateRepository,times(1)).save(giftCertificate);
    }
    @Test
    void updateFieldEntityTest(){
        BigDecimal price = BigDecimal.valueOf(145.01);
        Integer duration = 2 ;
        List<Tag> tags = List.of(new Tag(1L,"New year",List.of(giftCertificate)));
        when(giftCertificateRepository.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        giftCertificate.setDuration(duration);
        giftCertificate.setPrice(price);
        giftCertificate.setTags(tags);
        giftCertificateService.updateFieldEntity(giftCertificate);
        assertEquals(duration,giftCertificate.getDuration());
        assertEquals(price,giftCertificate.getPrice());
        assertEquals(tags,giftCertificate.getTags());
    }
    @Test
    void updateFieldEntityTestDoesNotExist(){
        when(giftCertificateRepository.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        GiftCertificate giftCertificateTest = new GiftCertificate();
        assertThrows(NoSuchEntityException.class,()->giftCertificateService.updateFieldEntity(giftCertificateTest));
    }
    @Test
    void updateFieldEntityTestNull(){
        GiftCertificate giftCertificateTest = new GiftCertificate(2L,giftCertificate.getName()
                ,giftCertificate.getDescription(),giftCertificate.getPrice(),giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),giftCertificate.getLastUpdateDate(),giftCertificate.getTags());
        when(giftCertificateRepository.findById(giftCertificateTest.getId())).thenReturn(Optional.of(giftCertificateTest));
        giftCertificateTest.setDuration(null);
        giftCertificateTest.setPrice(null);
        assertThrows(WrongDataException.class,()->giftCertificateService.updateFieldEntity(giftCertificateTest));
    }
    @Test
    void getCertificatesWithTagsByPartOfDescriptionTest(){
        String description = "Congratulations";
        when(giftCertificateRepository.getGiftCertificateByPartOfDescription(description))
                .thenReturn(List.of(giftCertificate));
        List<GiftCertificate> giftCertificatesTest = giftCertificateService
                .getCertificatesWithTagsByPartOfDescription(description);
        GiftCertificate giftCertificateTest = giftCertificatesTest.stream().findFirst().orElseThrow();
        assertEquals(giftCertificateTest.getId(),giftCertificate.getId());
        assertEquals(giftCertificateTest.getName(),giftCertificate.getName());
        assertEquals(giftCertificateTest.getDescription(),giftCertificate.getDescription());
        assertEquals(giftCertificateTest.getCreateDate(),giftCertificate.getCreateDate());
        assertEquals(giftCertificateTest.getPrice(),giftCertificate.getPrice());
        assertEquals(giftCertificateTest.getDuration(),giftCertificate.getDuration());
        assertEquals(giftCertificateTest.getLastUpdateDate(),giftCertificate.getLastUpdateDate());
        assertEquals(giftCertificateTest.getTags(),giftCertificate.getTags());
    }
    @Test
    void getCertificatesWithTagsSortByCreateDateASC(){
        int page = 1;
        int size = 10;
        GiftCertificate giftCertificateTest = new GiftCertificate(2L,giftCertificate.getName()
                ,giftCertificate.getDescription(),giftCertificate.getPrice(),giftCertificate.getDuration(),
                Instant.now(),giftCertificate.getLastUpdateDate(),giftCertificate.getTags());
        List<GiftCertificate> giftCertificatesTest = new ArrayList<>();
        giftCertificatesTest.add(giftCertificate);
        giftCertificatesTest.add(giftCertificateTest);
        List<GiftCertificate> giftCertificateList = giftCertificatesTest.stream()
                .sorted(Comparator.comparing(GiftCertificate::getCreateDate)).toList();
        when(giftCertificateRepository.getGiftCertificatesSortedByCreateDateASC(PageRequest.of(page,size)))
                .thenReturn(new PageImpl<>(giftCertificatesTest.stream()
                        .sorted(Comparator.comparing(GiftCertificate::getCreateDate)).toList()));
        List<GiftCertificate> result = giftCertificateService
                .getCertificatesWithTagsSortByCreateDateASC(page,size).stream().toList();
        for (int i = 0; i < result.size(); i++) {
            assertEquals(result.get(i).getId(),giftCertificateList.get(i).getId());
            assertEquals(result.get(i).getName(),giftCertificateList.get(i).getName());
            assertEquals(result.get(i).getCreateDate(),giftCertificateList.get(i).getCreateDate());
            assertEquals(result.get(i).getDuration(),giftCertificateList.get(i).getDuration());
            assertEquals(result.get(i).getLastUpdateDate(),giftCertificateList.get(i).getLastUpdateDate());
            assertEquals(result.get(i).getPrice(),giftCertificateList.get(i).getPrice());
            assertEquals(result.get(i).getDescription(),giftCertificateList.get(i).getDescription());
            assertEquals(result.get(i).getTags(),giftCertificateList.get(i).getTags());
        }
    }
    @Test
    void addTagTest(){
        Tag tag = new Tag(1L,"Apple shop",new ArrayList<>());
        when(giftCertificateRepository.save(giftCertificate)).thenReturn(giftCertificate);
        if(giftCertificate.getTags().contains(tag)){
            giftCertificate.removeTag(tag.getId());
        }
        giftCertificateService.addTag(giftCertificate,tag);
        assertEquals(giftCertificate.getTags(),List.of(tag));
    }
    @Test
    void addTagTestAlreadyExist(){
        Tag tag = new Tag(1L,"Apple shop",new ArrayList<>());
        giftCertificate.addTag(tag);
        assertThrows(WrongDataException.class,()->giftCertificateService.addTag(giftCertificate,tag));
    }
    @Test
    void removeTagTest(){
        Tag tag = new Tag(1L,"Apple shop",new ArrayList<>());
        GiftCertificate giftCertificateTest =giftCertificate;
        giftCertificate.addTag(tag);
        giftCertificateTest.addTag(tag);
        when(giftCertificateRepository.save(giftCertificate)).thenReturn(giftCertificate);
        giftCertificateService.removeTag(giftCertificate,tag.getId());
        giftCertificateTest.removeTag(tag.getId());
        assertEquals(giftCertificate.getTags(),giftCertificateTest.getTags());
    }
}
