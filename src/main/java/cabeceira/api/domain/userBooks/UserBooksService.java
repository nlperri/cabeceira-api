package cabeceira.api.domain.userBooks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cabeceira.api.domain.user.User;
import cabeceira.api.domain.user.UserRepository;
import cabeceira.api.domain.book.BookRepository;
import cabeceira.api.domain.userBooks.dto.UpdateUserBooksDTO;
import cabeceira.api.domain.userBooks.dto.UserBooksDetailsDTO;
import cabeceira.api.infra.exception.ValidatorException;

@Service
public class UserBooksService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserBooksRepository userBooksRepository;

    @Autowired
    BookRepository bookRepository;

    public UserBooksDetailsDTO update(UpdateUserBooksDTO data, String userBookId, String userId) {

        userExists(userId);

        var userBook = userBooksExists(userBookId);

        var book = bookRepository.getReferenceById(userBook.getBook().getId());

        if (data.readedPages() != null) {
            if (data.readedPages() > book.getTotalPages()) {
                throw new ValidatorException("Número de páginas lidas inválido.");
            }
        }

        if (data.bookshelfStatus() == BookshelfStatus.READED) {
            var bookTotalpages = book.getTotalPages();
            data = new UpdateUserBooksDTO(BookshelfStatus.READED, bookTotalpages);
        }

        userBook.update(data);
        UserBooks userBookToSave = userBooksRepository.save(userBook);

        return new UserBooksDetailsDTO(userBookToSave);

    }

    public void delete(String userBookId, String userId) {

        userExists(userId);

        var userBook = userBooksExists(userBookId);

        userBooksRepository.delete(userBook);
    }

    private UserBooks userBooksExists(String userBookId) {
        var userBook = userBooksRepository.getReferenceById(userBookId);

        if (userBook == null) {
            throw new ValidatorException("Id de estante inválido.");
        }
        return userBook;
    }

    private User userExists(String userId) {
        var user = userRepository.getReferenceById(userId);

        if (user == null) {
            throw new ValidatorException("Id de usuário inválido.");
        }
        return user;
    }
}