package cabeceira.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cabeceira.api.domain.user.CreateUserDTO;
import cabeceira.api.domain.user.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    // Atualizar dado de cadastro
    // Alterar nome sobrenome e senha
    // Deletar conta (extra)

    @Autowired
    private UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> register(@RequestBody @Valid CreateUserDTO data) {
        var user = this.userService.register(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
