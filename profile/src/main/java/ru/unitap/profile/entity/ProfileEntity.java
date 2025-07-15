package ru.unitap.profile.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileEntity {

  @DocumentId
  private String documentId;

  private String id;
  private String fullName;
  private String nickName;
  private String avatarUrl;
  private String phone;
  private String email;
  private String company;
  private String position;
  private String description;
  private String portfolio;
}
