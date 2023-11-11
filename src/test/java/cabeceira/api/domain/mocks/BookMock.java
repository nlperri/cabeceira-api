package cabeceira.api.domain.mocks;

import cabeceira.api.domain.author.Author;
import cabeceira.api.domain.book.Book;

import java.util.HashSet;
import java.util.Set;

public class BookMock {
    public static Book create() {
        Set<Author> authors = new HashSet<>();
        Author author1 = new Author(
                "123",
                "author 1"
        );
        Author author2 = new Author(
                "456",
                "author2"
        );
        authors.add(author1);
        authors.add(author2);

        return new Book(
                "123",
                "some title",
                200,
                "some cover",
                "some description",
                "2022-01-01",
                "some publisher",
                authors
        );
    }
}
