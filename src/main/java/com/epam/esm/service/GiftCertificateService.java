package com.epam.esm.service;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Interface {@code GiftCertificateService} describes abstract behavior for working with {@link GiftCertificate} objects.
 * @author Oleksandr Myronenko
 */
public interface GiftCertificateService extends CRUDService<GiftCertificate>{
    /**
     * Method for getting list of gift certificates with tags.
     * @param description description of certificate's entity to get
     * @return list of gift certificates
     */
    List<GiftCertificate> getCertificatesWithTagsByPartOfDescription(String description);

    /**
     * Method for getting page of gift certificates with tags sorted by create date.
     * @param page number of page
     * @param size number of size on page
     * @return page of gift certificates
     */
    Page<GiftCertificate> getCertificatesWithTagsSortByCreateDateASC(int page,int size);

    /**
     * This method is used to update gift certificate.
     * @param giftCertificate gift certificate
     */
    void updateFieldEntity(GiftCertificate giftCertificate);

    /**
     * Method for adding tag to gift certificate.
     * @param giftCertificate gift certificate
     * @param tag tag
     */
    void addTag(GiftCertificate giftCertificate, Tag tag);

    /**
     * Method for removing tag from gift certificate.
     * @param giftCertificate gift certificate
     * @param tagId tag's id
     */
    void removeTag(GiftCertificate giftCertificate, Long tagId);
}
