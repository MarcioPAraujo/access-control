package edu.umc.access_control.User;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor-based dependency injection is recommended for services.
     * It makes dependencies explicit and required.
     *
     * @param userRepository  The repository for user data access.
     * @param passwordEncoder The bean for encoding passwords.
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user in the system.
     *
     * @param username The desired username.
     * @param email    The user's email address.
     * @param password The user's raw (un-hashed) password.
     * @param role     The role assigned to the user.
     * @return The newly created UserModel.
     * @throws IllegalStateException if the username or email is already taken.
     */
    public UserModel registerUser(String username, String email, String password, Role role) {
        // 1. Validate that the username isn't already taken
        if (userRepository.existsByUsername(username)) {
            throw new IllegalStateException("Error: Username is already taken!");
        }

        // 2. Validate that the email isn't already in use
        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("Error: Email is already in use!");
        }

        // 3. Create a new UserModel instance
        UserModel newUser = new UserModel();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setRole(role);

        // 4. IMPORTANT: Hash the password before saving
        newUser.setPassword(passwordEncoder.encode(password));

        newUser.setVerified(false); // New users are not verified by default

        // 5. Save the new user to the database via the repository
        return userRepository.save(newUser);
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username to search for.
     * @return An Optional containing the UserModel if found.
     */
    public Optional<UserModel> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Finds a user by their email.
     *
     * @param email The email to search for.
     * @return An Optional containing the UserModel if found.
     */
    public Optional<UserModel> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @return An Optional containing the deleted UserModel if it existed.
     */
    public Optional<UserModel> deleteById(String id) {
        Optional<UserModel> user = userRepository.findById(id);
        user.ifPresent(userRepository::delete);
        return user;
    }
}
