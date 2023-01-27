package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface {@code TagRepository} describes tagRepository operations extending JPA repository for working with database tables.
 * @author Oleksandr Myronenko
 */
@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

    /**
     * Method for getting list of tags by tag's name from table.
     * @param name tag's name
     * @return list of tags
     */
    List<Tag> getTagsByName(@Param("name") String name);
    /**
     * Method for getting the most popular tag's id of user with the highest cost of orders from table.
     * @return tag's id
     */
    @Query(value = "select ct.tag_id from gift_certificate g join" +
            "    certificate_tag ct on g.id = ct.certificate_id" +
            "    where g.id in" +
            "    (select o.gift_certificate_id from orders o" +
            "     where o.id in" +
            "     (select ord.id from orders ord" +
            "      group by ord.user_id,ord.id order by sum(ord.price) desc ))" +
            "    group by ct.tag_id order by count(ct.tag_id) desc limit 1",nativeQuery = true)
    Long findIdTheMostPopularTagOfUserWithTheHighestCostOfOrders();
}
