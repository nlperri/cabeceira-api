package cabeceira.api.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cabeceira.api.domain.user.dto.CreateUserDTO;
import cabeceira.api.domain.user.dto.UserDetailsDTO;

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

    public void update() {

    }
}
