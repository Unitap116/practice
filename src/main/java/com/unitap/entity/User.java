package com.unitap.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import com.unitap.dictionary.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collectionName = "users")
public class User {

    @DocumentId
    private String id;
    private String email;
    private String password;
    private UserRole role;

}




