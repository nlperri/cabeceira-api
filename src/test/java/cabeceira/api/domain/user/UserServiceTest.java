package cabeceira.api.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cabeceira.api.domain.mocks.CreateUserDTOMock;
import cabeceira.api.domain.mocks.UpdateUserDTOMock;
import cabeceira.api.domain.user.dto.CreateUserDTO;
import cabeceira.api.domain.user.dto.UpdateUserDTO;
import cabeceira.api.domain.user.dto.UserDetailsDTO;
import cabeceira.api.infra.exception.ValidatorException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder;
    User user;
    CreateUserDTO createUserDTO;
    UserDetailsDTO userDetailsDTO;
    UpdateUserDTO updateUserDTO;

    @BeforeEach
    public void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        createUserDTO = CreateUserDTOMock.create();
        user = new User(createUserDTO);
        updateUserDTO = UpdateUserDTOMock.create();
    }

    @Test
    @DisplayName("should save the user with the encrypted password in the database and return a UserDetailsDTO")
    void testRegisterUser(){
        when(this.userRepository.save(user)).thenReturn(user);
        String encriptedPassword = passwordEncoder.encode(createUserDTO.password());
        user.setPassword(encriptedPassword);
       
        userDetailsDTO = this.userService.register(createUserDTO);
        
        assertEquals(createUserDTO.name(), userDetailsDTO.name());
        assertEquals(createUserDTO.lastName(), userDetailsDTO.lastName());
        assertEquals(createUserDTO.email(), userDetailsDTO.email());
        assertEquals(encriptedPassword, user.getPassword());
    }

    @Test
    @DisplayName("should update the user from an updateUserDTO with all the provided data.")
    void testUpdateUserCase1(){
        when(this.userRepository.getReferenceById("existingId")).thenReturn(user);
        String encriptedPassword = passwordEncoder.encode(updateUserDTO.password());
        updateUserDTO = new UpdateUserDTO(updateUserDTO.name(), updateUserDTO.lastName(), encriptedPassword);
        user.update(updateUserDTO);
        when(this.userRepository.save(user)).thenReturn(user);

       
        userDetailsDTO = this.userService.update(updateUserDTO, "existingId");

        verify(userRepository).save(user);
        assertEquals(updateUserDTO.name(), userDetailsDTO.name());
        assertEquals(updateUserDTO.lastName(), userDetailsDTO.lastName());
        assertTrue(passwordEncoder.matches(updateUserDTO.password(), user.getPassword()));
    }

    @Test
    @DisplayName("should update the user from an updateUserDTO with only a non-null attribute provided")
    void testUpdateUserCase2(){
        when(this.userRepository.getReferenceById("existingId")).thenReturn(user);
        updateUserDTO = new UpdateUserDTO(null, updateUserDTO.lastName(), null);
        user.update(updateUserDTO);
        when(this.userRepository.save(user)).thenReturn(user);

       
        userDetailsDTO = this.userService.update(updateUserDTO, "existingId");

        verify(userRepository).save(user);
        assertEquals(user.getName(), userDetailsDTO.name());
        assertEquals(updateUserDTO.lastName(), userDetailsDTO.lastName());
        assertNotEquals(null, user.getPassword());
    }

    @Test
    @DisplayName("should throw ValidatorException when user id is invalid in update method")
    void testUpdateUserCase3(){
        when(this.userRepository.getReferenceById("nonExistentId")).thenReturn(null);

       
        assertThrows(ValidatorException.class, ()->this.userService.update(updateUserDTO, "nonExistentId"));

    }

    @Test
    @DisplayName("should get the user by id and return a UserDetailsDTO")
    void testGetByIdCase1(){
        when(this.userRepository.getReferenceById("existingId")).thenReturn(user);

        userDetailsDTO = this.userService.getById("existingId");

        assertEquals(user.getName(), userDetailsDTO.name());
        assertEquals(user.getLastName(), userDetailsDTO.lastName());
        assertEquals(user.getEmail(), userDetailsDTO.email());

    }

    @Test
    @DisplayName("should throw ValidatorException when user id is invalid in getById method")
    void testGetByIdCase2(){
        when(this.userRepository.getReferenceById("nonExistentId")).thenReturn(null);
       
        assertThrows(ValidatorException.class, ()->this.userService.getById("nonExistentId"));

    }
}
