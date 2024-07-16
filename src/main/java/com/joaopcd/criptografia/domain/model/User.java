package com.joaopcd.criptografia.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userDocument;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<Card> cards = new HashSet<>();

    @PrePersist
    @PreUpdate
    private void prePersist() {
        cards.forEach(card -> card.setUserId(this.id));
    }

    public void addCard(Card card){
        card.setUserId(this.id);
        this.cards.add(card);
    }
}
