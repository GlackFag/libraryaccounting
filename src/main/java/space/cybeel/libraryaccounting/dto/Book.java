package space.cybeel.libraryaccounting.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@ToString
public class Book extends DataTransferObject{
    @Getter
    @Setter
    private int year;

    @Getter
    @Setter
    private int authorId;

    @Getter
    @Setter
    private Author author;

    @Getter
    @Setter
    @NotEmpty(message = "Title should not be empty")
    private String title;

    @Setter
    @Getter
    private int id;

    @Getter
    @Setter
    private String formedInfo;

    public Book(int year, int authorId, String title, int id) {
        this.year = year;
        this.authorId = authorId;
        this.title = title;
        this.id = id;
    }

    public Book(int id) {
        this.id = id;
    }

    public Book() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return year == book.year && authorId == book.authorId && Objects.equals(author, book.author) && title.equals(book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, author, title);
    }
}
