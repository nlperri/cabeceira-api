package cabeceira.api.domain.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BookRepository extends JpaRepository<Book, String> {

}