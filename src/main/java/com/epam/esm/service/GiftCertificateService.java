package com.epam.esm.service;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;

import java.util.List;
import java.util.Map;

/**
 * Interface {@code GiftCertificateService} describes abstract behavior for working with {@link GiftCertificate} objects.
 * @author Oleksandr Myronenko
 */
public interface GiftCertificateService extends CRUDService<GiftCertificate>{
    /**
     * Method for getting map where key is list of certificates, value is list of tags by specific description.
     * @param description description of certificate's entity to get
     * @return map where key is list of certificates, value is list of tags
     */
    Map<List<GiftCertificate>,List<Tag>> getCertificatesWithTagsByPartOfDescription(String description);

    /**
     * Method for getting map where key is list of certificates , value is list of tags by create date asc
     * @return map where key is list of certificates, value is list of tags
     */
    Map<List<GiftCertificate>,List<Tag>>  getCertificatesWithTagsSortByCreateDateASC();
}
