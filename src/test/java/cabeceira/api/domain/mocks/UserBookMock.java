package cabeceira.api.domain.mocks;

import cabeceira.api.domain.book.Book;
import cabeceira.api.domain.user.User;
import cabeceira.api.domain.userBooks.BookshelfStatus;
import cabeceira.api.domain.userBooks.UserBooks;

public class UserBookMock {
    public static UserBooks create(User user, Book book) {
        return new UserBooks(user, book, BookshelfStatus.WANT_TO_READ, 0);
    }

    public static UserBooks create(User user, Book book, String id) {
        return new UserBooks(id ,user, book, BookshelfStatus.WANT_TO_READ, 0);
    }
}
