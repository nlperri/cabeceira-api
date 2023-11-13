package cabeceira.api.controller;

import cabeceira.api.domain.author.Author;
import cabeceira.api.domain.author.AuthorRepository;
import cabeceira.api.domain.book.Book;
import cabeceira.api.domain.book.BookRepository;
import cabeceira.api.domain.mocks.BookMock;
import cabeceira.api.domain.mocks.UpdateUserBooksDTOMock;
import cabeceira.api.domain.mocks.UserMock;
import cabeceira.api.domain.user.User;
import cabeceira.api.domain.user.UserRepository;
import cabeceira.api.domain.user.dto.CreateUserDTO;
import cabeceira.api.domain.userBooks.UserBooks;
import cabeceira.api.domain.userBooks.UserBooksRepository;
import cabeceira.api.domain.userBooks.dto.UpdateUserBooksDTO;
import cabeceira.api.infra.security.TokenService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Set;

import cabeceira.api.domain.mocks.UserBookMock;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class UserBooksControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserBooksRepository userBooksRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JacksonTester<UpdateUserBooksDTO> updateUserBooksJson;

    private String validToken;
    private User user;
    private Book book;
    private UserBooks userBook;
    private Set<Author> authors;

    @BeforeEach
    void setUp() {
        user = UserMock.create();
        book = BookMock.create();
        authors = book.getAuthors();

        userRepository.save(user);

        for (Author author : authors) {
            authorRepository.save(author);
        }

        bookRepository.save(book);

        userBook = UserBookMock.create(user, book);
        userBook = userBooksRepository.save(userBook);

        validToken = "Bearer " + tokenService.generateToken(userRepository.findById(user.getId()).orElseThrow());
    }

    @AfterEach
    void tearDown() {
        userBooksRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    @WithMockUser
    @DisplayName("It should return a http status ok request when provided valid information to update user book.")
    void testUpdateUserBook() throws Exception {

        UpdateUserBooksDTO updateUserBooksData = UpdateUserBooksDTOMock.create();

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .put("/bookshelfs/" + userBook.getId())
                .header("Authorization", validToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateUserBooksJson.write(
                        updateUserBooksData).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser
    @DisplayName("It should return a http status ok request when delete user book.")
    void testDeleteUserBook() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/bookshelfs/" + userBook.getId())
                .header("Authorization", validToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser
    @DisplayName("It should return a http status ok request when provides valid book id.")
    void testGetUserBookId() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .get("/bookshelfs/" + book.getId())
                .header("Authorization", validToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser
    @DisplayName("It should return a http status ok when getting user books.")
    void testFetchUserBooks() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .get("/bookshelfs")
                .header("Authorization", validToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
