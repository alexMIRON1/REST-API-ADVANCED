package com.epam.esm.web.dto;

import com.epam.esm.web.serializer.InstantSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.Instant;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends RepresentationModel<OrderDto> {
    private Long id;
    private BigDecimal price;
    @JsonSerialize(using = InstantSerializer.class)
    private Instant purchaseTime;
    @JsonIgnore
    private UserDto user;

    private GiftCertificateDto giftCertificate;
    @Override
    @NonNull
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", price=" + price +
                ", purchaseTime=" + purchaseTime +
                ", user=" + user.getId() +
                ", giftCertificate=" + giftCertificate.getId() +
                '}';
    }
}
