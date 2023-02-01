package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.NoSuchEntityException;
import com.epam.esm.service.exception.WrongDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
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
    public Page<GiftCertificate> getAll(int page, int size) {
        return giftCertificateRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public void insert(GiftCertificate model) {
        if(model == null){
            log.error("gift certificate is null");
            throw new WrongDataException("Gift certificate was null");
        }
        model.setCreateDate(Instant.now());
        model.setLastUpdateDate(Instant.now());
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
    public void update(GiftCertificate giftCertificate) {
        giftCertificate.setLastUpdateDate(Instant.now());
        giftCertificateRepository.save(giftCertificate);

    }

    @Override
    @Transactional
    public void updateFieldEntity(GiftCertificate entity) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(entity.getId())
                .orElseThrow(()->new NoSuchEntityException("Gift certificate with id " + entity.getId()
                        + " does not exist"));
        if(entity.getDuration()==null && entity.getPrice() == null){
            log.error("duration and price are null");
            throw new WrongDataException("Duration and price are null");
        }
        if(entity.getDuration()!=null){
            giftCertificate.setDuration(entity.getDuration());
            giftCertificate.setLastUpdateDate(Instant.now());
            log.info("successfully updated duration " + entity.getDuration() + " for " + giftCertificate);
        }
        if(entity.getPrice()!=null){
            giftCertificate.setPrice(entity.getPrice());
            giftCertificate.setLastUpdateDate(Instant.now());
            log.info("successfully updated price " + entity.getPrice() + " for " + giftCertificate);
        }
        if(!entity.getTags().isEmpty()){
            giftCertificate.setTags(entity.getTags());
            giftCertificate.setLastUpdateDate(Instant.now());
            log.info("successfully updated tags " + entity.getTags() + " for " + giftCertificate);
            giftCertificateRepository.save(giftCertificate);
        }
    }

    @Override
    public List<GiftCertificate> getCertificatesWithTagsByPartOfDescription(String description) {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.
                getGiftCertificateByPartOfDescription(description);
        log.info("successfully got list  " + giftCertificates +  " by part of description " + description);
        return giftCertificates;
    }

    @Override
    public  Page<GiftCertificate> getCertificatesWithTagsSortByCreateDateASC(int page,int size) {
        Page<GiftCertificate> giftCertificates = giftCertificateRepository.
                getGiftCertificatesSortedByCreateDateASC(PageRequest.of(page, size));
        log.info("successfully got list  " + giftCertificates + " sorted by create date asc ");
        return giftCertificates;
    }

    @Override
    @Transactional
    public void addTag(GiftCertificate giftCertificate,Tag tag) {
        if (giftCertificate.getTags().contains(tag)){
            log.error("This tag" + tag + " already was in gift certificate " + giftCertificate);
            throw new WrongDataException("Such tag already exist in this certificate " + giftCertificate.getTags());
        }
        giftCertificate.addTag(tag);
        giftCertificate.setLastUpdateDate(Instant.now());
        giftCertificateRepository.save(giftCertificate);
        log.info("successfully added tag " + tag +  " to gift certificate " + giftCertificate);
    }

    @Override
    @Transactional
    public void removeTag(GiftCertificate giftCertificate,Long tagId) {
        giftCertificate.removeTag(tagId);
        giftCertificate.setLastUpdateDate(Instant.now());
        giftCertificateRepository.save(giftCertificate);
        log.info("successfully deleted tag by tag's id " + tagId + " from gift certificate " + giftCertificate );
    }
}
