package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
