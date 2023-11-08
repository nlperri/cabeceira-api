package cabeceira.api.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO(
                @NotBlank String name,
                @NotBlank String lastName,
                @NotBlank @Email String email,
                @NotBlank String password) {

}
