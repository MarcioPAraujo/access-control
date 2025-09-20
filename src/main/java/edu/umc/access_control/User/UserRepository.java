package edu.umc.access_control.User;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
  /**
   * Finds a user by their unique username. Spring Data automatically implements
   * this
   * method based on its name.
   *
   * @param username The username to search for.
   * @return An Optional containing the user if found, otherwise empty.
   */
  Optional<UserModel> findByUsername(String username);

  /**
   * Finds a user by their unique email address.
   * The field in the UserEntity is 'email', so the method is 'findByEmail'.
   *
   * @param email The email address to search for.
   * @return An Optional containing the user if found, otherwise empty.
   */
  Optional<UserModel> findByEmail(String email);

  /**
   * Checks if a user exists with the given email address. This is more efficient
   * than fetching the whole document if you only need to check for existence.
   *
   * @param email The email to check.
   * @return true if a user with that email exists, false otherwise.
   */
  Boolean existsByEmail(String email);

  /**
   * Checks if a user exists with the given username.
   *
   * @param username The username to check.
   * @return true if a user with that username exists, false otherwise.
   */
  Boolean existsByUsername(String username);
}
