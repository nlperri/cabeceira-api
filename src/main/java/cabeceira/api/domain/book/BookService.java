package cabeceira.api.domain.book;

import org.springframework.beans.factory.annotation.Autowired;
import cabeceira.api.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import cabeceira.api.infra.exception.ValidatorException;
import org.springframework.web.client.RestTemplate;
import cabeceira.api.domain.userBooks.BookshelfStatus;
import cabeceira.api.domain.userBooks.UserBooks;
import cabeceira.api.domain.userBooks.UserBooksRepository;
import cabeceira.api.domain.userBooks.dto.UserBookDetailsDTO;
import cabeceira.api.domain.author.Author;
import cabeceira.api.domain.author.AuthorRepository;

import java.util.Set;
import java.util.HashSet;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserBooksRepository userBooksRepository;

    private final String url = "https://www.googleapis.com/books/v1/volumes/%s";

    public UserBookDetailsDTO add(String userId, String bookId) {
        var user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new ValidatorException("Id de usuário inválido.");
        }

        var book = bookRepository.findById(bookId);

        if (book.isEmpty()) {

            var bookFromApi = restTemplate.getForObject(url.formatted(bookId), BookVolume.class);

            var authors = bookFromApi.volumeInfo.authors;

            Set<Author> authorSet = new HashSet<>();

            for (String authorName : authors) {
                Author author = new Author();
                author.setName(authorName);
                authorSet.add(author);
                authorRepository.save(author);
            }

            var imageLink = bookFromApi.volumeInfo.imageLinks.thumbnail;

            if (imageLink == null) {
                imageLink = bookFromApi.volumeInfo.imageLinks.smallThumbnail;
            }

            var savedBook = new Book(
                    bookFromApi.id,
                    bookFromApi.volumeInfo.title,
                    Integer.parseInt(bookFromApi.volumeInfo.pageCount),
                    imageLink,
                    bookFromApi.volumeInfo.description,
                    bookFromApi.volumeInfo.publishedDate,
                    bookFromApi.volumeInfo.publisher,
                    authorSet);

            savedBook = bookRepository.save(savedBook);

            var userBook = new UserBooks(
                    user.get(),
                    savedBook,
                    BookshelfStatus.WANT_TO_READ,
                    0);

            userBooksRepository.save(userBook);

            return new UserBookDetailsDTO(userBook);
                
        }

        var userBook = new UserBooks(
                user.get(),
                book.get(),
                BookshelfStatus.WANT_TO_READ,
                0);

        userBooksRepository.save(userBook);

        return new UserBookDetailsDTO(userBook);

    }
}
