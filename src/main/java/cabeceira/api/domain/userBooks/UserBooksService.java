package cabeceira.api.domain.userBooks;


import org.springframework.stereotype.Service;

import cabeceira.api.domain.userBooks.dto.UpdateUserBooksDTO;
import cabeceira.api.domain.userBooks.dto.UserBookDetailsDTO;
import cabeceira.api.infra.exception.ValidatorException;

@Service
public class UserBooksService {
    
    private UserBooksRepository userBooksRepository;

    public UserBookDetailsDTO update(UpdateUserBooksDTO data, String id){
        UserBooks userBook = userBooksRepository.getReferenceById(id);
        if(userBook == null){
            throw new ValidatorException("Userbook not found");
        }
        userBook.update(data);
        return new UserBookDetailsDTO(userBook);
    }
}
