package com.epam.esm.web.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.web.dto.GiftCertificateDto;
import com.epam.esm.web.dto.TagDto;
import com.epam.esm.web.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class {@code TagController} represents endpoint of API which allows you to do operations on tags.
 * {@code TagController} is accessed to send request by /tags.
 * @author Oleksandr Myronenko
 */
@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    private final GiftCertificateService certificateService;
    private final Converter<Tag, TagDto> tagConverter;
    private final Converter<GiftCertificate,GiftCertificateDto> certificateConverter;
    private final PagedResourcesAssembler<Tag> pagedResourcesAssembler;

    /**
     * This method is used to get tag dto by tag's id.
     * @param id tag's id
     * @return tag dto
     */
    @GetMapping("/{tagId}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto getById(@PathVariable("tagId") Long id){
        return tagConverter.toModel(tagService.getById(id));
    }

    /**
     * This method is used to get page of tags dto.
     * @param page number of page
     * @return page of tags dto
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<TagDto> getAll(@RequestParam(defaultValue = "0") int page){
        Page<Tag> tags = tagService.getAll(page,10);
        return pagedResourcesAssembler.toModel(tags,tagConverter);
    }

    /**
     * This method is used to create tag.
     * @param tagDto tag
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody TagDto tagDto){
        tagService.insert(tagConverter.toEntity(tagDto));
    }

    /**
     * This method is used to add tag to gift certificate
     * @param tagDto tag
     * @param certId gift certificate's id
     */
    @PostMapping("/certificate/{certId}")
    @ResponseStatus(HttpStatus.OK)
    public void addTagToCertificate(@RequestBody TagDto tagDto,
                                    @PathVariable("certId") Long certId){
        GiftCertificateDto giftCertificate = certificateConverter.toModel(certificateService.getById(certId));
        TagDto tag = tagConverter.toModel(tagService.getById(tagDto.getId()));
        certificateService.addTag(certificateConverter.toEntity(giftCertificate),
                tagConverter.toEntity(tag));
    }

    /**
     * This method is used to delete tag from certificate.
     * @param tagDto tag
     * @param certId gift certificate's id
     */
    @DeleteMapping("/certificate/{certId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeTagFromCertificate(@RequestBody TagDto tagDto
            ,@PathVariable("certId") Long certId){
        GiftCertificateDto giftCertificateDto = certificateConverter.toModel(certificateService.getById(certId));
        TagDto tag = tagConverter.toModel(tagService.getById(tagDto.getId()));
        certificateService.removeTag(certificateConverter.toEntity(giftCertificateDto),
                tag.getId());
    }

    /**
     * This method is used to delete tag by id.
     * @param id tag's id
     */
    @DeleteMapping("/{tagId}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable("tagId") Long id){
        TagDto tagDto = tagConverter.toModel(tagService.getById(id));
        tagService.remove(tagConverter.toEntity(tagDto));
    }

    /**
     * This method is used to get list of gift certificates and list of tags by tag's name.
     * @param name tag's name
     * @return map, key is list of gift certificates dto, value is list of tags dto
     */
    @GetMapping("/certificates")
    @ResponseStatus(HttpStatus.OK)
    public Map<List<GiftCertificateDto>, List<TagDto>> getCertificatesWithTagsByName(
            @RequestParam(value = "name") String name){
        return tagService.getCertificatesWithTagsByName(name).entrySet()
                .stream().collect(Collectors.toMap(k->k.getKey()
                        .stream().map(certificateConverter::toModel).toList()
                        ,v->v.getValue().stream().map(tagConverter::toModel).toList()));
    }
    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public TagDto getTheMostWidelyUsedTagOfUserWithTheHighestCostOfOrders(){
        return tagConverter.toModel(tagService.getTheMostWidelyUsedTagOfUserWithTheHighestCostOfOrders());
    }
}
