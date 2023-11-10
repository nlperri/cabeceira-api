package cabeceira.api.domain.userBooks.dto;

import java.util.Set;

import cabeceira.api.domain.author.dto.AuthorDTO;
import cabeceira.api.domain.userBooks.BookshelfStatus;

public record UserBooksWithBookDetailsDTO(
        String userId,
        String bookId,
        BookshelfStatus bookshelfStatus,
        Integer readedPages,
        String id,
        String title,
        int totalPages,
        String cover,
        String description,
        String publishedDate,
        String publisher,
        Set<AuthorDTO> authors

) {
}
