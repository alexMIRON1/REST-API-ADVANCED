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
    @Query("select t from tag t where t.name =:name")
    List<Tag> getTagsByName(@Param("name") String name);

    /**
     * Method for getting list of tags by part of certificate's description from table.
     * @param description part of description
     * @return list of tags
     */
    @Query(" select t from tag t join t.certificates c where c.description like :description")
    List<Tag> getTagsByPartOfDescription(@Param("description") String description);

    /**
     * Method for getting list of tags sorted by certificate's create date from table.
     * @return list of tags
     */
    @Query("select t from tag t join t.certificates c order by c.createDate asc")
    List<Tag> getTagsSortedByCreateDateASC();
}
