package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.GiftCertificateRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.NoSuchEntityException;
import com.epam.esm.service.exception.WrongDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public GiftCertificate getById(Long id) {
        if (id==null){
            log.error("id is null");
            throw new WrongDataException("Id was null");
        }
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(id);
        if(giftCertificate.isEmpty()){
            log.error("gift certificate was not found");
            throw new NoSuchEntityException("Gift certificate with id " + id +  " doesn't exist");
        }
        log.info("successfully got gift certificate "+ giftCertificate.get() + " by id -> " + id);
        return giftCertificate.get();
    }

    @Override
    public List<GiftCertificate> getAll() {
        return giftCertificateRepository.findAll();
    }

    @Override
    public void insert(GiftCertificate model) {
        if(model == null){
            log.error("gift certificate is null");
            throw new WrongDataException("Gift certificate was null");
        }
        giftCertificateRepository.save(model);
        log.info("successfully created gift certificate -> " + model);
    }

    @Override
    public void remove(GiftCertificate item) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateRepository
                .findById(item.getId());
        if(giftCertificateOptional.isEmpty()){
            log.error("gift certificate is null -> " +item );
            throw new NoSuchEntityException("This certificate with id " + item.getId() + " does not exist");
        }
        giftCertificateRepository.delete(giftCertificateOptional.get());
        log.info("successfully deleted gift certificate -> " + item);
    }

    @Override
    @Transactional
    public void update(GiftCertificate entity) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(entity.getId())
                .orElseThrow(()->new NoSuchEntityException("Gift certificate with id " + entity.getId()
                        + " does not exist"));
        if(entity.getDuration()==null && entity.getPrice() == null){
            log.error("duration and price are null");
            throw new WrongDataException("Duration and price are null");
        }
        if(entity.getDuration()!=null){
            giftCertificate.setDuration(entity.getDuration());
            log.info("successfully updated duration " + entity.getDuration() + " for " + giftCertificate);
        }
        if(entity.getPrice()!=null){
            giftCertificate.setPrice(entity.getPrice());
            log.info("successfully updated price " + entity.getPrice() + " for " + giftCertificate);
        }

    }

    @Override
    public Map<List<GiftCertificate>, List<Tag>> getCertificatesWithTagsByPartOfDescription(String description) {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.
                getGiftCertificateByPartOfDescription(description);
        List<Tag> tags = tagRepository.getTagsByPartOfDescription(description);
        checkCertificatesAndTags(giftCertificates,tags);
        Map<List<GiftCertificate>, List<Tag>> result = new HashMap<>();
        result.put(giftCertificates,tags);
        log.info("successfully got map " + result +  " by part of description " + description);
        return result;
    }

    @Override
    public Map<List<GiftCertificate>, List<Tag>> getCertificatesWithTagsSortByCreateDateASC() {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.
                getGiftCertificatesSortedByCreateDateASC();
        List<Tag> tags  = tagRepository.getTagsSortedByCreateDateASC();

        checkCertificatesAndTags(giftCertificates,tags);

        Map<List<GiftCertificate>, List<Tag>> result = new HashMap<>();
        result.put(giftCertificates,tags);
        log.info("successfully got map " + result + " sorted by create date asc ");
        return result;
    }

    /**
     * Method for checking that list of gift certificates or/and tags are empty.
     * @param giftCertificates list of gift certificates
     * @param tags list of tags
     */
    private void checkCertificatesAndTags(List<GiftCertificate> giftCertificates,
                                             List<Tag> tags){
        if(giftCertificates.isEmpty() || tags.isEmpty()){
            log.error("gift certificates " + giftCertificates + " are empty or tag are empty " + tags );
            throw new NoSuchEntityException("List of gift certificates or/and tags are empty");
        }

    }
}
