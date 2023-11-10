package cabeceira.api.domain.userBooks.dto;
import java.util.stream.Collectors;

import java.util.Set;

import cabeceira.api.domain.author.dto.AuthorDTO;
import cabeceira.api.domain.userBooks.BookshelfStatus;
import cabeceira.api.domain.userBooks.UserBooks;

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
        public UserBooksWithBookDetailsDTO(UserBooks userBooks) {
        this(
                userBooks.getUser().getId(),
                userBooks.getBook().getId(),
                userBooks.getBookshelfStatus(),
                userBooks.getReadedPages(),
                userBooks.getId(),
                userBooks.getBook().getTitle(),
                userBooks.getBook().getTotalPages(),
                userBooks.getBook().getCover(),
                userBooks.getBook().getDescription(),
                userBooks.getBook().getPublishedDate(),
                userBooks.getBook().getPublisher(),
                userBooks.getBook().getAuthors().stream()
                        .map(AuthorDTO::new)
                        .collect(Collectors.toSet())
        );
    }
}
