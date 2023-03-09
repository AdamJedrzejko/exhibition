package com.cats.exhibition.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PublicVote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "publicvotegenerator")
    private Integer id;
    @Column(unique = true)
    private Integer ticketId;
    private Integer catId;

    public PublicVote(Integer ticketId, Integer catId) {
        this.ticketId = ticketId;
        this.catId = catId;
    }
}
