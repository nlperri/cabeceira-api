package cabeceira.api.domain.mocks;

import cabeceira.api.domain.user.dto.CreateUserDTO;

public class CreateUserDTOMock {
    public static CreateUserDTO create() {
        return new CreateUserDTO(
            "john@example.com",
            "password123", 
            "John", 
            "Doe"
        );
    }
}
