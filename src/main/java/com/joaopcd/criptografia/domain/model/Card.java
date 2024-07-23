package com.joaopcd.criptografia.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_card")
public class Card {

    @Id
    private String creditCardToken;

    @Column(precision = 13, scale = 2)
    private BigDecimal value;

    @Column(name = "user_id")
    private Long userId;
}
