package com.epam.esm.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public class TagDto {
    private Long id;
    private String name;
    @JsonIgnore
    private Set<GiftCertificateDto> certificates = new HashSet<>();
}
