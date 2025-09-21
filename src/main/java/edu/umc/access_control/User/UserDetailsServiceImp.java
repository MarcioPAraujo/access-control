package edu.umc.access_control.User;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
  private final UserRepository userRepository;

  public UserDetailsServiceImp(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * This method is called by Spring Security to fetch user details.
   *
   * @param username The username submitted in the login form.
   * @return A UserDetails object that Spring Security can use for authentication.
   * @throws UsernameNotFoundException if the user is not found in the database.
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // 1. Fetch the user from your database using the username.
    UserModel userEntity = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

    // 2. Convert the user's roles (Set<String>) into a list of GrantedAuthority
    // objects.
    // Spring Security requires roles to be in the format 'ROLE_ROLENAME'.
    List<GrantedAuthority> authorities = userEntity.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
        .collect(Collectors.toList());

    // 3. Convert your UserEntity into a Spring Security User object.
    // The User object implements the UserDetails interface.
    // It requires the username, the HASHED password, and a collection of
    // authorities (roles).
    return new User(
        userEntity.getUsername(),
        userEntity.getPassword(),
        authorities // Pass the list of authorities here.
    );
  }
}
