package cabeceira.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import cabeceira.api.domain.user.UserService;
import cabeceira.api.domain.user.dto.CreateUserDTO;
import cabeceira.api.domain.user.dto.UpdateUserDTO;
import cabeceira.api.domain.user.dto.UserDetailsDTO;
import cabeceira.api.infra.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Transactional
    public ResponseEntity<UserDetailsDTO> register(@RequestBody @Valid CreateUserDTO data) {

        var user = this.userService.register(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @SecurityRequirement(name = "bearer-key")
    @PutMapping()
    @Transactional
    public ResponseEntity<UserDetailsDTO> update(@RequestBody UpdateUserDTO data) {

        String userId = tokenService.getLoggedUserId();

        var user = this.userService.update(data, userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @SecurityRequirement(name = "bearer-key")
    @GetMapping()
    public ResponseEntity<UserDetailsDTO> getById() {

        String id = tokenService.getLoggedUserId();

        var user = this.userService.getById(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
