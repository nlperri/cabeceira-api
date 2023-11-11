package cabeceira.api.domain.userBooks;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserBooksRepository extends JpaRepository<UserBooks, String> {

    Optional<UserBooks> findByBookId(String bookId);

    Optional<List<UserBooks>> findAllByUserId(String userId);

}
