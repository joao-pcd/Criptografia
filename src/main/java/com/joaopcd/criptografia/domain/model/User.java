package com.joaopcd.criptografia.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userDocument;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<Card> cards = new HashSet<>();

}
