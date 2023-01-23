package com.epam.esm.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.impl.IndexedListSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
@Getter
@Setter
@ToString
public class OrderDto {
    private Long id;
    private BigDecimal price;
    @JsonSerialize(using = IndexedListSerializer.class)
    private Instant purchaseTime;
    private UserDto user;
    private GiftCertificateDto giftCertificate;
}
