package cabeceira.api.domain.user.dto;

import cabeceira.api.domain.user.User;

public record UserDetailsDTO(
        String id,
        String name,
        String lastName,
        String email) {

    public UserDetailsDTO(User user) {
        this(user.getId(), user.getName(), user.getLastName(), user.getEmail());
    }
}
