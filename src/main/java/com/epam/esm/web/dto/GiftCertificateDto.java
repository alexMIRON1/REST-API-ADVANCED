package com.epam.esm.web.dto;

import com.epam.esm.web.deserializer.GiftCertificatePeriodDeserializer;
import com.epam.esm.web.serializer.InstantSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.Period;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Getter
@Setter
@ToString
public class GiftCertificateDto {
    private Long id;
    private String name;
    private BigDecimal price;
    @JsonDeserialize(using = GiftCertificatePeriodDeserializer.class)
    private Period duration;
    @JsonSerialize(using = InstantSerializer.class)
    private Instant createDate;
    @JsonSerialize(using = InstantSerializer.class)
    private Instant lastUpdateDate;
    private Set<TagDto> tags = new HashSet<>();

    public void addTag(TagDto tag){
        this.tags.add(tag);
        tag.getCertificates().add(this);
    }
    public void removeTag(Long tagId){
        TagDto tag = this.tags.stream().filter(t-> Objects.equals(t.getId(), tagId)).findFirst().orElse(null);
        if(tag!=null){
            this.tags.remove(tag);
            tag.getCertificates().remove(this);
        }
    }
}
