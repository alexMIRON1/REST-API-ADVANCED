package com.epam.esm.layer.service;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.GiftCertificateRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.NoSuchEntityException;
import com.epam.esm.service.exception.WrongDataException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TagServiceTest{
     @Autowired
     private TagService tagService;
     @Autowired
     private Tag tag;
     @Autowired
     private GiftCertificate giftCertificate;
     @MockBean
     private GiftCertificateRepository giftCertificateRepository;
     @MockBean
     private TagRepository tagRepository;
     @Test
     void getByIdTest(){
       when(tagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));
       Tag tagTest = tagService.getById(tag.getId());
       assertEquals(tagTest.getId(),tag.getId());
       assertEquals(tagTest.getName(),tag.getName());
       assertEquals(tagTest.getCertificates(),tag.getCertificates());
     }
     @Test
     void getByIdTestNull(){
        assertThrows(WrongDataException.class,()->tagService.getById(null));
     }
     @Test
     void getByIdTestNoSuchEntity(){
        when(tagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));
        assertThrows(NoSuchEntityException.class,()->tagService.getById(5L));
     }
     @Test
     void getAllTest(){
        int page = 1;
        int size = 10;
        when(tagRepository.findAll(PageRequest.of(page,size))).thenReturn(new PageImpl<>(List.of(tag)));
        Tag tagTest = tagService.getAll(page,size).stream().findFirst().orElseThrow();
        assertEquals(tagTest.getId(),tag.getId());
        assertEquals(tagTest.getName(),tag.getName());
        assertEquals(tagTest.getCertificates(),tag.getCertificates());
     }
     @Test
     void insertTest(){
         when(tagRepository.save(tag)).thenReturn(tag);
         tagService.insert(tag);
         verify(tagRepository,times(1)).save(tag);
     }
     @Test
     void insertTestNull(){
          assertThrows(WrongDataException.class,()->tagService.insert(null));
     }
     @Test
     void removeTest(){
          when(tagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));
          doNothing().when(tagRepository).deleteById(tag.getId());
          tagService.remove(tag);
          verify(tagRepository,times(1)).deleteById(tag.getId());
     }
     @Test
     void removeTestNoSuchEntity(){
         when(tagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));
         Tag tagTest = new Tag();
         assertThrows(NoSuchEntityException.class,()->tagService.remove(tagTest));
     }
     @Test
     void getCertificatesWithTagsByNameTest(){
          String name = "Apple shop";
          GiftCertificate giftCertificateTest = new GiftCertificate(2L, giftCertificate.getName(),
                  giftCertificate.getDescription(),giftCertificate.getPrice(),
                  giftCertificate.getDuration(),giftCertificate.getCreateDate(),
                  giftCertificate.getLastUpdateDate(),new ArrayList<>());
          giftCertificateTest.setTags(List.of(tag));
          List<Tag> tags = List.of(tag);
          List<GiftCertificate> giftCertificates = List.of(giftCertificateTest);
          when(tagRepository.getTagsByName(name)).thenReturn(tags);
          when(giftCertificateRepository.getGiftCertificateByTagName(name)).thenReturn(giftCertificates);
          Map<List<GiftCertificate>,List<Tag>> result = tagService.getCertificatesWithTagsByName(name);
          for (int i = 0; i < result.values().size(); i++) {
              List<List<Tag>> lists = result.values().stream().toList();
              for (int j = 0; j <lists.get(i).size(); j++) {
                  Tag testTag = lists.get(i).get(j);
                  assertEquals(testTag.getId(),  tags.get(j).getId());
                  assertEquals(testTag.getName(),  tags.get(j).getName());
                  assertEquals(testTag.getCertificates(),  tags.get(j).getCertificates());
              }
          }
     }
     @Test
    void getTheMostWidelyUsedTagOfUserWithTheHighestCostOfOrdersTest(){
         when(tagRepository.findIdTheMostPopularTagOfUserWithTheHighestCostOfOrders()).thenReturn(tag.getId());
         when(tagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));
         Tag tagTest = tagService.getTheMostWidelyUsedTagOfUserWithTheHighestCostOfOrders();
         assertEquals(tagTest.getId(),tag.getId());
         assertEquals(tagTest.getName(),tag.getName());
         assertEquals(tagTest.getCertificates(),tag.getCertificates());
     }
}
