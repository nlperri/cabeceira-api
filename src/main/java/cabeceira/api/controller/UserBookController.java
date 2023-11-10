package cabeceira.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import cabeceira.api.domain.userBooks.UserBooks;
import cabeceira.api.domain.userBooks.UserBooksService;
import cabeceira.api.domain.userBooks.dto.UpdateUserBooksDTO;
import cabeceira.api.domain.userBooks.dto.UserBooksDetailsDTO;
import cabeceira.api.infra.security.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping(value = "/bookshelfs")
public class UserBookController {

    @Autowired
    UserBooksService userBooksService;

    @Autowired
    TokenService tokenService;

    @PutMapping("/{userBookId}")
    @Transactional
    public ResponseEntity<UserBooksDetailsDTO> update(@RequestBody UpdateUserBooksDTO data,
            @PathVariable String userBookId) {
        String userId = this.tokenService.getLoggedUserId();

        UserBooksDetailsDTO userBook = userBooksService.update(data, userBookId, userId);
        return ResponseEntity.ok().body(userBook);
    }

    @DeleteMapping("/{userBookId}")
    @Transactional
    public ResponseEntity<String> delete(@PathVariable String userBookId) {
        String userId = this.tokenService.getLoggedUserId();
        userBooksService.delete(userBookId, userId);
        return ResponseEntity.ok().body("book successfully deleted");
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Object> getDetails(@PathVariable String bookId) {
        var userId = tokenService.getLoggedUserId();
        var bookDetails = userBooksService.getDetails(userId, bookId);
        return ResponseEntity.ok().body(bookDetails);
    }

    // @GetMapping()
    // public ResponseEntity<List<UserBooksWithBookDetailsDTO>> fetchUserBooks() {
    // var userId = tokenService.getLoggedUserId();
    // var userBooksList = userBooksService.fetchUserBooks(userId);
    // return ResponseEntity.ok().body(userBooksList);
    // }

}