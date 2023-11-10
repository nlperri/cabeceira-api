package cabeceira.api.domain.userBooks.dto;

import cabeceira.api.domain.userBooks.BookshelfStatus;

public record UpdateUserBooksDTO(
    BookshelfStatus bookshelfStatus,
    Integer readedPages
) { }
