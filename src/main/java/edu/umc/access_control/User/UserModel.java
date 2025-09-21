package edu.umc.access_control.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "users")
public class UserModel {

  /**
   * The primary identifier for the document. Mapped to MongoDB's `_id` field.
   * It's a String to accommodate MongoDB's ObjectId.
   */
  @Id
  private String id;

  /**
   * The user's chosen username.
   * We can add an index for faster queries on this field.
   */
  @Indexed(unique = true)
  private String username;

  /**
   * The user's email address.
   * This field is mapped to 'email_address' in the MongoDB document.
   * An index is created to ensure emails are unique and for fast lookups.
   */
  @Field("email_address")
  @Indexed(unique = true)
  private String email;

  /**
   * The user's hashed password.
   */
  private String password;

  /**
   * A flag to indicate the role of the user (e.g., manager, leader, employee).
   * Mapped to the 'role' field in MongoDB.
   */
  private Set<String> roles = new HashSet<>();

  /**
   * A flag to indicate if the user has verified their email address.
   * Mapped to the 'is_verified' field in MongoDB.
   */
  @Field("is_verified")
  private boolean isVerified = false;

  /**
   * Timestamp of when the user account was created.
   */
  @Field("creation_date")
  private LocalDateTime creationDate;
}
