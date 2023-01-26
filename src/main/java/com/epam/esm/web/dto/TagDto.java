package com.epam.esm.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TagDto extends RepresentationModel<TagDto> {
    private Long id;
    private String name;
    @JsonIgnore
    private List<GiftCertificateDto> certificates = new ArrayList<>();
    @Override
    @NonNull
    public String toString() {
        return "id:"+id +","+
                "name: "+name + ",";
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
