package com.unitap.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @DocumentId
    private String cardId;

    private String name;
    private Boolean isPublic;
    private Long views;
}
