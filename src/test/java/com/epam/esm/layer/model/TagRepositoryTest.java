package com.epam.esm.layer.model;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@DataJpaTest
@ComponentScan("com.epam.esm.layer")
class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private Tag tag;
    @Autowired
    private Order order;
    @Autowired
    private User user;
    @Autowired
    private GiftCertificate giftCertificate;

    @Test
    void getTagsByNameTest(){
        String name = tag.getName();
        testEntityManager.persistAndFlush(tag);
        List<Tag> tagTest = tagRepository.getTagsByName(name);
        assertThat(tagTest).extracting(Tag::getName).containsOnly(name);
    }
    @Test
    void findIdTheMostPopularTagOfUserWithTheHighestCostOfOrdersTest(){
        testEntityManager.persistAndFlush(tag);
        giftCertificate.setTags(List.of(tag));
        testEntityManager.persistAndFlush(giftCertificate);
        user.setOrders(new ArrayList<>());
        testEntityManager.merge(new Role(1,"User",new ArrayList<>()));
        testEntityManager.merge(user);
        user.setOrders(List.of(order));
        testEntityManager.merge(order);
        Long tagId = tagRepository.findIdTheMostPopularTagOfUserWithTheHighestCostOfOrders();
        assertEquals(tagId,tag.getId());
    }
}
