package edu.umc.access_control.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {

  private String username;

  private String email;

  private String password;

  private String role;
}
