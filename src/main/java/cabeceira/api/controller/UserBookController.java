package cabeceira.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cabeceira.api.domain.userBooks.UserBooksService;
import cabeceira.api.domain.userBooks.dto.UpdateUserBooksDTO;
import cabeceira.api.domain.userBooks.dto.UserBookDetailsDTO;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping(value = "/bookshelfs")
public class UserBookController {
    
    @Autowired
    UserBooksService userBooksService;

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<UserBookDetailsDTO> update(@RequestBody UpdateUserBooksDTO data,@PathVariable String id){
        UserBookDetailsDTO userBook = userBooksService.update(data, id);
        return ResponseEntity.ok().body(userBook);
    }
}
