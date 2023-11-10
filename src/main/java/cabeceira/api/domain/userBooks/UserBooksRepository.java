package cabeceira.api.domain.userBooks;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cabeceira.api.domain.userBooks.dto.UserBooksWithBookDetailsDTO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import cabeceira.api.domain.userBooks.dto.UserBooksWithBookDetailsDTO;

@Repository
public interface UserBooksRepository extends JpaRepository<UserBooks, String> {

    @Query("SELECT ub FROM UserBooks ub JOIN FETCH ub.book b WHERE ub.user.id =:userId")
    List<UserBooksWithBookDetailsDTO> findByUserId(@Param("userId") String userId);

    @Query("SELECT ub FROM UserBooks ub LEFT JOIN FETCH ub.book b JOIN FETCH ub.user u WHERE b.id = :bookId AND u.id = :userId")
    Object findByBookIdAndUserId(@Param("bookId") String bookId, @Param("userId") String userId);

    Optional<Object> findByBookId(String bookId);

}
