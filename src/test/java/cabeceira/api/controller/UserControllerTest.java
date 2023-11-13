package cabeceira.api.controller;

import cabeceira.api.domain.author.AuthorRepository;
import cabeceira.api.domain.book.BookRepository;
import cabeceira.api.domain.mocks.CreateUserDTOMock;
import cabeceira.api.domain.mocks.UpdateUserDTOMock;
import cabeceira.api.domain.mocks.UserMock;
import cabeceira.api.domain.user.User;
import cabeceira.api.domain.user.UserRepository;
import cabeceira.api.domain.user.dto.CreateUserDTO;
import cabeceira.api.domain.user.dto.UpdateUserDTO;
import cabeceira.api.domain.userBooks.UserBooksRepository;
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


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class UserControllerTest {
    
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
    private JacksonTester<CreateUserDTO> registerJson;

    @Autowired
    private JacksonTester<UpdateUserDTO> updateUserJson;

    private String validToken;
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
    @DisplayName("It should return http status created when provided valid information")
    void testRegisterUser() throws Exception {
    
    CreateUserDTO createUserRegisterData = CreateUserDTOMock.create();
    
    MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerJson.write(
                    createUserRegisterData
                ).getJson())
                )
                .andReturn().getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @WithMockUser
    @DisplayName("it should return a http status bad request when email already exists")
    void testFailRegisterUser() throws Exception {
    
    CreateUserDTO createUserRegisterData = new CreateUserDTO(
        "john.doe@example.com", 
        "12345", 
        "john", 
        "doe"
        );
    
    MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerJson.write(
                    createUserRegisterData
                ).getJson())
                )
                .andReturn().getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser
    @DisplayName("it should return a http status ok request when provided valid information to update user")
    void testUpdateUser() throws Exception {
    
    UpdateUserDTO updateUserData = UpdateUserDTOMock.create();

    MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .put("/users")
                .header("Authorization", validToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateUserJson.write(
                    updateUserData
                ).getJson())
                )
                .andReturn().getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("it should return a http status bad request when provided valid information to update user")
    void testFailUpdateUser() throws Exception {
    
        User invalidUser = new User(
            "UUIDFALSO123",
            "someemail@email.com",
            "123123",
            "some name",
            "some lastname"
    );

    String invalidToken = "Bearer " + tokenService.generateToken(invalidUser);

    MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .put("/users")
                .header("Authorization", invalidToken)
                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();
                assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser
    @DisplayName("It should return http status ok when is logged")
    void testGetUserById() throws Exception {
    
    MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                .get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
