package com.joaopcd.criptografia.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "tb_card")
public class Card {

    @Id
    private String creditCardToken;

    @Column(precision = 13, scale = 2)
    private BigDecimal value;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
