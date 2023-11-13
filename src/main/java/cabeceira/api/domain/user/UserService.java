package cabeceira.api.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cabeceira.api.domain.user.dto.CreateUserDTO;
import cabeceira.api.domain.user.dto.UpdateUserDTO;
import cabeceira.api.domain.user.dto.UserDetailsDTO;
import cabeceira.api.infra.exception.ValidatorException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDetailsDTO register(CreateUserDTO data) {
        var user = new User(data);
        var encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        user.setPassword(encryptedPassword);
        this.userRepository.save(user);
        var userDetails = new UserDetailsDTO(user);

        return userDetails;
    }

    public UserDetailsDTO update(UpdateUserDTO data, String userId) {
        var user = userRepository.getReferenceById(userId);
        if (user == null) {
            throw new ValidatorException("Id de usuário inválido.");
        }
        if(data.password() != null) {
            var encriptedPassword = new BCryptPasswordEncoder().encode(data.password());
            data = new UpdateUserDTO(data.name(), data.lastName(), encriptedPassword);
        }
            
        user.update(data);
        userRepository.save(user);

        return new UserDetailsDTO(user);
    }

    public UserDetailsDTO getById(String id) {
        var user = userRepository.getReferenceById(id);
        if (user == null) {
            throw new ValidatorException("Id de usuário inválido.");
        }
        return new UserDetailsDTO(user);
    }
}
