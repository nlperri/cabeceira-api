package cabeceira.api.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

public record CreateUserDTO(
    @NotBlank @Email 
    String email,
    @NotBlank @Size(min = 5)
    String password,
    @NotBlank
    String name,
    @NotBlank
    String lastName
    ) {
    
}
