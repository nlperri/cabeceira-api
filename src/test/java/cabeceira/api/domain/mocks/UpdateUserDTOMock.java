package cabeceira.api.domain.mocks;

import cabeceira.api.domain.user.dto.UpdateUserDTO;

public class UpdateUserDTOMock {
    public static UpdateUserDTO create() {
        return new UpdateUserDTO(
            "John", 
            "Smith", 
            "123456"
        );
    }
}
