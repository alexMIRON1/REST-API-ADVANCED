package com.epam.esm.model.repository;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface {@code GiftCertificateRepository} describes giftCertificateRepository operations extending JPA repository for working with database tables.
 * @author Oleksandr Myronenko
 */
@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate,Long>{
    /**
     * Method for getting list of certificates by part of certificate's description from table.
     * @param description part of description
     * @return list of certificates
     */
    @Query("select g from gift_certificate g  where g.description like %:description%")
    List<GiftCertificate> getGiftCertificateByPartOfDescription(@Param("description") String description);

    /**
     * Method for getting gift certificates with tags sorted by gift certificates create date asc from table.
     * @param pageRequest page request for number of page and size
     * @return page of gift certificates
     */

    @Query("select c from gift_certificate c order by c.createDate asc")
    Page<GiftCertificate> getGiftCertificatesSortedByCreateDateASC(PageRequest pageRequest);

    /**
     * Method for getting list of certificates by tag's name from table.
     * @param name tag's name
     * @return list of certificates
     */
    @Query("select g from gift_certificate g join g.tags t  where t.name = :name")
    List<GiftCertificate> getGiftCertificateByTagName(@Param("name") String name);

}
