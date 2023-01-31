package com.epam.esm.web.converter.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.web.dto.GiftCertificateDto;
import com.epam.esm.web.converter.Converter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class GiftCertificateConverter
        implements Converter<GiftCertificate, GiftCertificateDto> {
    private final ModelMapper modelMapper;

    @Override
    @NonNull
    public GiftCertificateDto toModel(@NonNull GiftCertificate entity) {
        GiftCertificateDto certificateDto = modelMapper.map(entity,GiftCertificateDto.class);
        log.info("certificate entity " + entity + " was successfully converted to model " + certificateDto);
        return certificateDto;
    }

    @Override
    public GiftCertificate toEntity(GiftCertificateDto dto) {
        if (dto != null) {
            GiftCertificate giftCertificate = modelMapper.map(dto, GiftCertificate.class);
            log.info("certificate model was successfully converted to entity");
            return giftCertificate;
        }
        log.debug("certificate model was not converted to entity -> model is null");
        return null;
    }
}
