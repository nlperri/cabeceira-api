package cabeceira.api.domain.book;

import cabeceira.api.domain.author.AuthorRepository;
import cabeceira.api.domain.mocks.BookMock;
import cabeceira.api.domain.mocks.BookVolumeMock;
import cabeceira.api.domain.mocks.UserBookMock;
import cabeceira.api.domain.mocks.UserMock;
import cabeceira.api.domain.user.User;
import cabeceira.api.domain.user.UserRepository;
import cabeceira.api.domain.userBooks.BookshelfStatus;
import cabeceira.api.domain.userBooks.UserBooks;
import cabeceira.api.domain.userBooks.UserBooksRepository;
import cabeceira.api.domain.userBooks.dto.UserBooksDetailsDTO;
import cabeceira.api.infra.exception.ValidatorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

class BookServiceTest {


    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private UserBooksRepository userBooksRepository;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It should add book when book id from api provided it's not registered.")
    void testAddNewBook() {

        User user = UserMock.create();
        Book book = BookMock.create();
        BookVolume bookVolume = BookVolumeMock.create();
        UserBooks userBooks = UserBookMock.create(user, book);

        BDDMockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        BDDMockito.when(bookRepository.findById("nonExistingBookId")).thenReturn(Optional.empty());
        BDDMockito.when(restTemplate.getForObject(Mockito.anyString(),Mockito.eq(BookVolume.class))).thenReturn(bookVolume);
        BDDMockito.when(bookRepository.save(book)).thenReturn(book);
        BDDMockito.when(userBooksRepository.save(any())).thenReturn(userBooks);

        UserBooksDetailsDTO result = bookService.add(user.getId(), "bookId");

        Assertions.assertEquals(user.getId(), result.user().id());
        Assertions.assertEquals(book.getId(), result.book().id());
        Assertions.assertEquals(BookshelfStatus.WANT_TO_READ, result.bookshelfStatus());
        Assertions.assertEquals(0, result.readedPages());
    }

    @Test
    @DisplayName("It should not add book when book id from api provided it's already registered.")
    void testBookAlreadyRegistered() {

        User user = UserMock.create();
        Book book = BookMock.create();
        UserBooks userBooks = UserBookMock.create(user, book);

        BDDMockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        BDDMockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        BDDMockito.when(userBooksRepository.save(any())).thenReturn(userBooks);

        UserBooksDetailsDTO result = bookService.add(user.getId(), book.getId());

        Assertions.assertEquals(user.getId(), result.user().id());
        Assertions.assertEquals(book.getId(), result.book().id());
        Assertions.assertEquals(BookshelfStatus.WANT_TO_READ, result.bookshelfStatus());
        Assertions.assertEquals(0, result.readedPages());
    }

    @Test
    @DisplayName("It should throw RestClientException when book id from api doesn't exist.")
    void testRestClientExceptionOnNonExistingBook() {
        User user = UserMock.create();

        BDDMockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        BDDMockito.when(bookRepository.findById("nonExistingBookId")).thenReturn(Optional.empty());
        BDDMockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(BookVolume.class)))
                .thenThrow(new RestClientException("Simulating RestClientException"));

        Assertions.assertThrows(RestClientException.class, () -> {
            bookService.add(user.getId(), "nonExistingBookId");
        });
    }

    @Test
    @DisplayName("It should throw ValidatorException when user id is invalid.")
    void testValidatorExceptionOnInvalidUserId() {

        BDDMockito.when(userRepository.findById("invalidUserId")).thenReturn(Optional.empty());

        Assertions.assertThrows(ValidatorException.class, () -> {
            bookService.add("invalidUserId", "someBookId");
        });
    }
}