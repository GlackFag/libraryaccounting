package space.cybeel.libraryaccounting.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
public class Author extends DataTransfetObject{
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Getter
    @Setter
    @NotEmpty
    @Length(min = 2, message = "Name should be at least 2 characters")
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @OneToMany(mappedBy = "author")
    private List<Book> books = new ArrayList<>();

    @Transient
    public static final Author UNKNOWN_AUTHOR = new Author("Unknown Author", -1) {
        @Override
        public void setName(String name) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setId(int id) {
            throw new UnsupportedOperationException();
        }
    };

    public Author(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Author(int id) {
        this.id = id;
    }

    public Author() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return name.equals(author.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void addBook(Book b){
        getBooks().add(b);
        b.setAuthor(this);
    }
}
