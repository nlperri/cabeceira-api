package cabeceira.api.domain.book;

import java.util.Set;
import java.util.List;

import cabeceira.api.domain.author.Author;
import cabeceira.api.domain.userBooks.UserBooks;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Book {

    @Id
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(name = "total_pages")
    private int totalPages;

    private String cover;

    private String description;

    @Column(name = "published_date")
    private String publishedDate;

    private String publisher;

    @ManyToMany
    @JoinTable(name = "books_authors", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    @OneToMany(mappedBy = "book")
    private List<UserBooks> usersBooks;

}