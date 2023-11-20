package cabeceira.api.domain.mocks;

import cabeceira.api.domain.userBooks.dto.UpdateUserBooksDTO;
import cabeceira.api.domain.userBooks.BookshelfStatus;

public class UpdateUserBooksDTOMock {
  public static UpdateUserBooksDTO create() {
    return new UpdateUserBooksDTO(
        BookshelfStatus.READING,
        0);
  }
}
