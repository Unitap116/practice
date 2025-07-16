package com.unitap.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private UUID cardId;
    private String name;
    private Boolean isPublic;
    private Long views;
}
