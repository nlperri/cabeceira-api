package cabeceira.api.domain.userBooks;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBooksRepository extends JpaRepository<UserBooks, UUID> {

}
