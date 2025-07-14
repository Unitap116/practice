package ru.itis.unitap.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.google.cloud.spring.data.firestore.Document;
import lombok.NoArgsConstructor;
import ru.itis.unitap.dictionary.UserRole;

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




