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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
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

    private User user;
    private Book book;
    private BookVolume bookVolume;
    private UserBooks userBooks;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = UserMock.create();
        book = BookMock.create();
        bookVolume = BookVolumeMock.create();
        userBooks = UserBookMock.create(user, book);
    }

    @Test
    @DisplayName("It should add book when book id from api provided it's not registered.")
    void testAddNewBook() {

        BDDMockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        BDDMockito.when(bookRepository.findById("nonExistingBookId")).thenReturn(Optional.empty());
        BDDMockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(BookVolume.class)))
                .thenReturn(bookVolume);
        BDDMockito.when(bookRepository.save(book)).thenReturn(book);
        BDDMockito.when(userBooksRepository.save(any())).thenReturn(userBooks);

        UserBooksDetailsDTO result = bookService.add(user.getId(), "nonExistingBookId");

        Assertions.assertEquals(user.getId(), result.user().id());
        Assertions.assertEquals(book.getId(), result.book().id());
        Assertions.assertEquals(BookshelfStatus.WANT_TO_READ, result.bookshelfStatus());
        Assertions.assertEquals(0, result.readedPages());
    }

    @Test
    @DisplayName("It should not add book when book id from api provided it's already registered.")
    void testBookAlreadyRegistered() {

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