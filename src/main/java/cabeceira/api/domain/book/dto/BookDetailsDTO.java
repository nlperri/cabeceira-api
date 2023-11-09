package cabeceira.api.domain.book.dto;

import java.util.Set;
import java.util.stream.Collectors;

import cabeceira.api.domain.author.Author;
import cabeceira.api.domain.author.dto.AuthorDTO;
import cabeceira.api.domain.book.Book;

public record BookDetailsDTO(
        String id,
        String title,
        int totalPages,
        String cover,
        String description,
        String publishedDate,
        String publisher,
        Set<AuthorDTO> authors) {

    public BookDetailsDTO(Book book) {
        this(
                book.getId(),
                book.getTitle(),
                book.getTotalPages(),
                book.getCover(),
                book.getDescription(),
                book.getPublishedDate(),
                book.getPublisher(),
                mapAuthorsToDTO(book.getAuthors()));
    }

    private static Set<AuthorDTO> mapAuthorsToDTO(Set<Author> authors) {
        return authors.stream()
                .map(author -> new AuthorDTO(author.getId(), author.getName()))
                .collect(Collectors.toSet());
    }
}