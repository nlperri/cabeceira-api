package cabeceira.api.domain.userBooks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import cabeceira.api.domain.userBooks.BookshelfStatus;

public record UpdateUserBooksDTO(
    @JsonProperty("bookshelfStatus")
    BookshelfStatus bookshelfStatus,
    @JsonProperty("readedPages")
    Integer readedPages
) { }
