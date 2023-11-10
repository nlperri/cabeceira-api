package cabeceira.api.domain.userBooks.dto;

import cabeceira.api.domain.book.dto.BookDetailsDTO;
import cabeceira.api.domain.user.dto.UserDetailsDTO;
import cabeceira.api.domain.userBooks.BookshelfStatus;
import cabeceira.api.domain.userBooks.UserBooks;

public record UserBooksDetailsDTO(
        UserDetailsDTO user,
        BookDetailsDTO book,
        BookshelfStatus bookshelfStatus,
        Integer readedPages) {

    public UserBooksDetailsDTO(UserBooks userBooks) {
        this(
                new UserDetailsDTO(userBooks.getUser()),
                new BookDetailsDTO(userBooks.getBook()),
                userBooks.getBookshelfStatus(),
                userBooks.getReadedPages());
    }
}
