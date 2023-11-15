package cabeceira.api.controller;

import org.springframework.web.bind.annotation.RestController;
import jakarta.transaction.Transactional;

import cabeceira.api.domain.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import cabeceira.api.domain.userBooks.dto.UserBooksDetailsDTO;
import cabeceira.api.infra.security.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = "http://localhost:5173")
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/{bookId}")
    @Transactional
    public ResponseEntity<UserBooksDetailsDTO> add(@PathVariable String bookId) {

        var userId = tokenService.getLoggedUserId();

        var book = bookService.add(userId, bookId);
        return ResponseEntity.ok().body(book);
    }
}
