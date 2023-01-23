package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.GiftCertificateRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.NoSuchEntityException;
import com.epam.esm.service.exception.WrongDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final GiftCertificateRepository certificateRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, GiftCertificateRepository certificateRepository) {
        this.tagRepository = tagRepository;
        this.certificateRepository = certificateRepository;
    }

    @Override
    public Tag getById(Long id) {
        if(id==null){
            log.error("id is null");
            throw new WrongDataException("Id was null");
        }
        Optional<Tag> tagOptional = tagRepository.findById(id);
        if(tagOptional.isEmpty()){
            log.error("tag " + tagOptional + " doesn't  exist");
            throw new NoSuchEntityException("Tag with id " + id + " does not exist");
        }
        log.info("successfully go tag " + tagOptional + " by id " + id);
        return tagOptional.get();
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Override
    public void insert(Tag model) {
        if(model==null){
            log.error("model is null");
            throw new WrongDataException("Tag was null");
        }
        tagRepository.save(model);
        log.info("successfully created tag -> " + model );
    }

    @Override
    public void remove(Tag item) {
        Optional<Tag> tagOptional = tagRepository.findById(item.getId());
        if(tagOptional.isEmpty()){
            log.error("tag is empty " + tagOptional);
            throw new NoSuchEntityException("This tag with id " + item.getId() + " does not exist");
        }
        tagRepository.delete(tagOptional.get());
        log.info("successfully deleted tag -> " + tagOptional);
    }

    @Override
    public Map<List<GiftCertificate>, List<Tag>> getCertificatesWithTagsByName(String name) {
        if (name==null){
            log.error("name is null");
            throw new WrongDataException("Name was null");
        }
        List<Tag> tags = tagRepository.getTagsByName(name);
        List<GiftCertificate> giftCertificates = certificateRepository.getGiftCertificateByTagName(name);
        if(tags.isEmpty() || giftCertificates.isEmpty()){
            log.error("tags are empty " + tags + " or gift certificates are empty " + giftCertificates );
            throw new NoSuchEntityException("List of tags and/or gift certificates are null");
        }
        Map<List<GiftCertificate>,List<Tag>> result = new HashMap<>();
        result.put(giftCertificates,tags);
        log.info("successfully got certificates with tags " + result + " by tag's name " + name);
        return result;
    }
}
