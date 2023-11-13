package cabeceira.api.controller;

import cabeceira.api.domain.author.AuthorRepository;
import cabeceira.api.domain.book.BookRepository;
import cabeceira.api.domain.mocks.UserMock;
import cabeceira.api.domain.user.User;
import cabeceira.api.domain.user.UserRepository;
import cabeceira.api.domain.user.dto.CreateUserDTO;
import cabeceira.api.domain.userBooks.UserBooksRepository;
import cabeceira.api.infra.security.TokenService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class BookControllerTest {

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


    private String validToken;
    private final String validBookIdFromApi = "-bF2CwAAQBAJ";
    private User user;


    @BeforeEach
    void setUp() {
       user = UserMock.create();
       userRepository.save(user);

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
    @DisplayName("It should return http status ok when providing valid book id.")
    void testAddBook() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .post("/books/" + validBookIdFromApi)
                .header("Authorization", validToken)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser
    @DisplayName("It should return http status service unavailable when providing invalid book id from api.")
    void testInvalidBookIdFromApi() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/books/invalidBookId")
                        .header("Authorization", validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE.value());
    }

    @Test
    @DisplayName("It should return http status forbidden when doesn't provide token.")
    void testWithoutToken() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/books/" + validBookIdFromApi)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("It should return http status forbidden when provides invalid token.")
    void testInvalidToken() throws Exception {

        User user = new User(new CreateUserDTO(
                "someemail@email.com",
                "123123",
                "some name",
                "some lastname"
        ));

        String invalidToken = "Bearer " + tokenService.generateToken(user);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/books/" + validBookIdFromApi)
                        .header("Authorization", invalidToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

}