package edu.umc.access_control.payloads;

import edu.umc.access_control.User.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {

  private String username;

  private String email;

  private String password;

  private Role role;
}
