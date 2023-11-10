package cabeceira.api.domain.userBooks;

import cabeceira.api.domain.book.Book;
import cabeceira.api.domain.user.User;
import cabeceira.api.domain.userBooks.dto.UpdateUserBooksDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(name = "bookshelf_status")
    private BookshelfStatus bookshelfStatus;

    @Column(name = "readed_pages")
    private Integer readedPages;

    public UserBooks(User user, Book book, BookshelfStatus bookshelfStatus, Integer readedPages) {
        this.user = user;
        this.book = book;
        this.bookshelfStatus = bookshelfStatus;
        this.readedPages = readedPages;
    }

    public void update(UpdateUserBooksDTO data) {
        this.bookshelfStatus = data.bookshelfStatus() != null ? data.bookshelfStatus() : this.bookshelfStatus;
        this.readedPages = data.readedPages() != null ? data.readedPages() : this.readedPages;
    }

}
