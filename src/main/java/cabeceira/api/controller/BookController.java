package cabeceira.api.controller;

import org.springframework.web.bind.annotation.RestController;
import jakarta.transaction.Transactional;


import cabeceira.api.domain.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import cabeceira.api.domain.userBooks.dto.UserBookDetailsDTO;


record DataBody(String userId){
    
}

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/{bookId}")
    @Transactional
    public ResponseEntity<UserBookDetailsDTO> add(@RequestBody DataBody data, @PathVariable String bookId) {
        var book = bookService.add(data.userId(), bookId);
        return ResponseEntity.ok().body(book);
    }
}
