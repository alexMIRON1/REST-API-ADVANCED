package com.epam.esm.service;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;

import java.util.List;
import java.util.Map;

/**
 * Interface {@code TagService} describes abstract behavior for working with {@link Tag} objects.
 * @author Oleksandr Myronenko
 */
public interface TagService extends CRDService<Tag>{
    /**
     * Method for getting map where key is list of certificates, value is list of tags by specific tag's name.
     * @param name name of tags to get
     * @return map where key is list of certificates, values is list of tags
     */
    Map<List<GiftCertificate>, List<Tag>> getCertificatesWithTagsByName(String name);
}
