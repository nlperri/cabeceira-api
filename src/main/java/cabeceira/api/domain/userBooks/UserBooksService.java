package cabeceira.api.domain.userBooks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        var user = userRepository.getReferenceById(userId);

        if (user == null) {
            throw new ValidatorException("Id de usuário inválido.");
        }

        var userBook = userBooksRepository.getReferenceById(userBookId);
        

        if (userBook == null) {
            throw new ValidatorException("Id de estante inválido.");
        }

        var book = bookRepository.getReferenceById(userBook.getBook().getId());

        if(data.readedPages() != null) {
            if(data.readedPages() > book.getTotalPages()) {
                throw new ValidatorException("Número de páginas lidas inválido.");
            }
        }

        if(data.bookshelfStatus() == BookshelfStatus.READED) {
            var bookTotalpages = book.getTotalPages();
            data = new UpdateUserBooksDTO(BookshelfStatus.READED, bookTotalpages);
        }

        userBook.update(data);
        UserBooks ubToSave = userBooksRepository.save(userBook);
        return new UserBooksDetailsDTO(ubToSave);

    }

    public void delete(String userBookId, String userId) {
        var user = userRepository.getReferenceById(userId);

        if (user == null) {
            throw new ValidatorException("Id de usuário inválido.");
        }

        var userBook = userBooksRepository.getReferenceById(userBookId);

        if (userBook == null) {
            throw new ValidatorException("Id de estante inválido.");
        }

        userBooksRepository.delete(userBook);
    }

    private boolean bookshelfExists(String userBookId) {
        var userBook = userBooksRepository.getReferenceById(userBookId);

        if (userBook == null) {
            return false;
        }
        return true;
    }

    private boolean userExists(String userId) {
        var user = userBooksRepository.getReferenceById(userId);

        if (user == null) {
            return false;
        }
        return true;
    }
}