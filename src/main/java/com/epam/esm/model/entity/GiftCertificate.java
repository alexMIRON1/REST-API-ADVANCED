package com.epam.esm.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "gift_certificate")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gift_certificate")
public class GiftCertificate {
    @Id
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    @Column(name = "create_date")
    private Instant createDate;
    @Column(name = "last_update_date")
    private Instant lastUpdateDate;
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "certificate_tag",joinColumns = @JoinColumn(name = "certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @ToString.Exclude
    private List<Tag> tags = new ArrayList<>();
    public void addTag(Tag tag){
        this.tags.add(tag);
        tag.getCertificates().add(this);
    }
    public void removeTag(Long tagId){
        Tag tag = this.tags.stream().filter(t-> Objects.equals(t.getId(), tagId)).findFirst().orElse(null);
        if(tag!=null){
            this.tags.remove(tag);
            tag.getCertificates().remove(this);
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GiftCertificate that = (GiftCertificate) o;
        return id != null && Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
