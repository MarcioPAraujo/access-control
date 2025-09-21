package edu.umc.access_control.payloads;

import edu.umc.access_control.User.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {

  @NotBlank(message = "O nome de usuário é obrigatório")
  @Size(min = 3, max = 20, message = "O nome de usuário deve ter entre 3 e 20 caracteres")
  private String username;

  @NotBlank(message = "O email é obrigatório")
  @Size(max = 50, message = "O email deve ter no máximo 50 caracteres")
  @Email(message = "O email deve ser válido")
  private String email;

  @NotBlank(message = "A senha é obrigatória")
  @Size(min = 8, max = 20, message = "A senha deve ter entre 8 e 20 caracteres")
  private String password;

  @NotBlank(message = "O nível de acesso deve ser especificado")
  private Role role;
}
