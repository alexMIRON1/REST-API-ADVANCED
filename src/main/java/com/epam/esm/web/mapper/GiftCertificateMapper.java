package com.epam.esm.web.mapper;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.web.dto.GiftCertificateDto;
import org.mapstruct.Mapper;

/**
 * Interface {@code GiftCertificateMapper} describes converting from dto {@link GiftCertificateDto} to entity {@link GiftCertificate} and vice versa.
 */
@Mapper(componentModel = "spring")
public interface GiftCertificateMapper {
    /**
     * Method for converting entity gift certificate to gift certificate dto
     * @param giftCertificate entity gift certificate
     * @return gift certificate dto
     */
    GiftCertificateDto toDto(GiftCertificate giftCertificate);

    /**
     * Method for converting gift certificate dto to entity gift certificate
     * @param giftCertificateDto gift certificate dto
     * @return entity gift certificate
     */
    GiftCertificate toEntity(GiftCertificateDto giftCertificateDto);
}
