package cabeceira.api.domain.author.dto;

import cabeceira.api.domain.author.Author;

public record AuthorDTO(
        String id,
        String name) {

    public AuthorDTO(Author author) {
        this(author.getId(), author.getName());
    }
}