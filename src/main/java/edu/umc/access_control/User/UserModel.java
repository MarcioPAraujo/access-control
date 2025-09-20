package edu.umc.access_control.User;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
  private Role role;

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

  // --- Constructors ---
  public UserModel() {
    this.creationDate = LocalDateTime.now();
  }

  public UserModel(String username, String email, String password, Role role) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
    this.creationDate = LocalDateTime.now();
  }

  // --- Getters and Setters ---
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public boolean isVerified() {
    return isVerified;
  }

  public void setVerified(boolean verified) {
    isVerified = verified;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }
}
