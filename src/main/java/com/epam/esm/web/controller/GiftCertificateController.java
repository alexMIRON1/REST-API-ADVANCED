package com.epam.esm.web.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.web.dto.GiftCertificateDto;
import com.epam.esm.web.mapper.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class GiftCertificateController {
    private final GiftCertificateService certificateService;
    private final Converter<GiftCertificate,GiftCertificateDto> certificateConverter;
    private final PagedResourcesAssembler<GiftCertificate> pagedResourcesAssembler;

    /**
     * This method is used to get all gift certificates.
     * @return list of gift certificates dto
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<GiftCertificateDto> getAll(@RequestParam(defaultValue = "0") int page){
            Page<GiftCertificate> giftCertificates = certificateService.getAll(page,10);
        return pagedResourcesAssembler.toModel(giftCertificates, certificateConverter);
    }

    /**
     * This method is used to get gift certificate by id.
     * @param id gift certificate's id
     * @return gift certificate dto
     */
    @GetMapping("/{certId}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto getById(@PathVariable("certId") Long id){
        return certificateConverter.toModel(certificateService.getById(id));
    }

    /**
     * This method for create gift certificate.
     * @param giftCertificateDto gift certificate dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody GiftCertificateDto giftCertificateDto){
        certificateService.insert(certificateConverter.toEntity(giftCertificateDto));
    }

    /**
     * This method is used to remove gift certificate.
     * @param giftCertificateDto gift certificate dto
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void remove(@RequestBody GiftCertificateDto giftCertificateDto){
        certificateService.remove(certificateConverter.toEntity(giftCertificateDto));
    }

    /**
     * This method is used to update gift certificate.
     * @param giftCertificateDto gift certificate dto
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody GiftCertificateDto giftCertificateDto){
        certificateService.updateFieldEntity(certificateConverter.toEntity(giftCertificateDto));
    }

    /**
     * This method is used to get certificates with tags by part of gift certificate's description
     * @param description gift certificate's description
     * @return list of gift certificates dto
     */
    @GetMapping("/tags/{certDescription}")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> getGiftCertificatesWithTagsByDescription(
            @PathVariable("certDescription") String description){
        return certificateService.getCertificatesWithTagsByPartOfDescription(description)
                .stream().map(certificateConverter::toModel).toList();
    }

    /**
     * This method is used to get gift certificates with tags sorted by gift certificate's create date asc/
     * @param page number of page
     * @return page model of gift certificates dto
     */
    @GetMapping("/tags/sortedAsc")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<GiftCertificateDto> getGiftCertificatesWithTagsSortedByCreateDateAsc(
            @RequestParam(defaultValue = "0") int page){
        Page<GiftCertificate> giftCertificates = certificateService
                .getCertificatesWithTagsSortByCreateDateASC(page,10);
        return pagedResourcesAssembler.toModel(giftCertificates,certificateConverter);
    }
}
