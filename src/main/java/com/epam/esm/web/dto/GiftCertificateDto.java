package com.epam.esm.web.dto;

import com.epam.esm.web.serializer.InstantSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    @JsonSerialize(using = InstantSerializer.class)
    private Instant createDate;
    @JsonSerialize(using = InstantSerializer.class)
    private Instant lastUpdateDate;
    private List<TagDto> tags = new ArrayList<>();
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
