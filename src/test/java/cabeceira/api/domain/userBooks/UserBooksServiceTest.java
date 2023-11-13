package cabeceira.api.domain.userBooks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import cabeceira.api.domain.user.UserRepository;
import cabeceira.api.domain.author.dto.AuthorDTO;
import cabeceira.api.domain.book.Book;
import cabeceira.api.domain.book.BookRepository;
import cabeceira.api.domain.mocks.BookMock;
import cabeceira.api.domain.mocks.UpdateUserBooksDTOMock;
import cabeceira.api.domain.mocks.UserBookMock;
import cabeceira.api.domain.mocks.UserMock;
import cabeceira.api.domain.user.User;
import cabeceira.api.domain.userBooks.dto.UpdateUserBooksDTO;
import cabeceira.api.domain.userBooks.dto.UserBooksDetailsDTO;
import cabeceira.api.domain.userBooks.dto.UserBooksWithBookDetailsDTO;

@ExtendWith(MockitoExtension.class)
public class UserBooksServiceTest {

    @InjectMocks
    private UserBooksService userBooksService;

    @Mock
    private UserBooksRepository userBooksRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository UserRepository;

    User user;
    Book book;
    UserBooks userBook;
    UpdateUserBooksDTO updateUserBooksDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = UserMock.create();
        book = BookMock.create();
        userBook = UserBookMock.create(user, book, "1");
        updateUserBooksDTO = UpdateUserBooksDTOMock.create();
    }

    @Test
    @DisplayName("It should update an user book when provides valid information.")
    void testUpdateUserBooks() {
        when(this.UserRepository.getReferenceById(user.getId())).thenReturn(user);
        when(this.userBooksRepository.getReferenceById(userBook.getId())).thenReturn(userBook);
        when(this.bookRepository.getReferenceById(book.getId())).thenReturn(book);
        userBook.update(updateUserBooksDTO);
        when(this.userBooksRepository.save(userBook)).thenReturn(userBook);
        
        UserBooksDetailsDTO userBooksDetailsDto = this.userBooksService.update(updateUserBooksDTO, userBook.getId(), user.getId());

        assertEquals(updateUserBooksDTO.bookshelfStatus(), userBooksDetailsDto.bookshelfStatus());
        assertEquals(updateUserBooksDTO.readedPages(), userBooksDetailsDto.readedPages());
    }

    @Test
    @DisplayName("It should receive book details when provides valid book id.")
    void testGetDetails() {
        when(this.UserRepository.getReferenceById(user.getId())).thenReturn(user);
        when(this.bookRepository.getReferenceById(book.getId())).thenReturn(book);
        when(this.userBooksRepository.findByBookId(book.getId())).thenReturn(Optional.of(userBook));

        UserBooksWithBookDetailsDTO ubWithDetails = this.userBooksService.getDetails(user.getId(), book.getId());

        assertEquals(userBook.getUser().getId(), ubWithDetails.userId());
        assertEquals(userBook.getBookshelfStatus(), ubWithDetails.bookshelfStatus());
        assertEquals(userBook.getBookshelfStatus(), ubWithDetails.bookshelfStatus());
        assertEquals(userBook.getReadedPages(), ubWithDetails.readedPages());
        assertEquals(userBook.getId(), ubWithDetails.id());
        assertEquals(userBook.getBook().getTitle(), ubWithDetails.title());
        assertEquals(userBook.getBook().getTotalPages(), ubWithDetails.totalPages());
        assertEquals(userBook.getBook().getCover(), ubWithDetails.cover());
        assertEquals(userBook.getBook().getDescription(), ubWithDetails.description());
        assertEquals(userBook.getBook().getPublishedDate(), ubWithDetails.publishedDate());
        assertEquals(userBook.getBook().getPublisher(), ubWithDetails.publisher());
        assertEquals(userBook.getBook().getAuthors()
                        .stream()
                        .map(AuthorDTO::new)
                        .collect(Collectors.toSet()), ubWithDetails.authors());
    }

    @Test
    @DisplayName("It should receive all books from logged user bookshelf.")
    void testFetchUserBooks() {
        when(this.UserRepository.getReferenceById(user.getId())).thenReturn(user);
        when(this.userBooksRepository.findAllByUserId(user.getId())).thenReturn(Optional.of(List.of(userBook)));
        
        
        List<UserBooksWithBookDetailsDTO> userBookDetailsList = this.userBooksService.fetchUserBooks(user.getId());

        assertEquals(List.of(userBook).stream()
        .map(UserBooksWithBookDetailsDTO::new)
        .collect(Collectors.toList()), userBookDetailsList);
    }

    @Test
    @DisplayName("It should delete a book from bookshelf when provides valid book id.")
    void testDeleteUserBooks() {
        when(this.UserRepository.getReferenceById(user.getId())).thenReturn(user);
        when(this.userBooksRepository.getReferenceById(userBook.getId())).thenReturn(userBook);

        this.userBooksService.delete(userBook.getId(), user.getId());
        
        verify(userBooksRepository).delete(userBook);
    }
}
