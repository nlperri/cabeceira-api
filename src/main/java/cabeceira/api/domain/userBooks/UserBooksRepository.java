package cabeceira.api.domain.userBooks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBooksRepository extends JpaRepository<UserBooks, String> {

}
