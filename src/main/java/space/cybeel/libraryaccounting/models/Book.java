package space.cybeel.libraryaccounting.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "book")
public class Book  extends DataTransfetObject{
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Getter
    @Setter
    @NotEmpty(message = "Title should not be empty")
    @Column(name = "title")
    private String title;

    @Getter
    @Setter
    @Column(name = "year")
    private int year;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "taker_id")
    private Person taker;

    @ManyToOne
    @JoinColumn(name = "authorId", referencedColumnName = "id")
    private Author author;

    @Getter
    @Setter
    @Transient
    private String formedInfo;

    public Book(int year, String title, int id) {
        this.year = year;
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
        return id == book.id && year == book.year && Objects.equals(title, book.title) && Objects.equals(taker, book.taker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, year);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", taker=" + taker +
                ", author=" + getAuthor().getName() +
                '}';
    }

    /**
     * Used for detached authors
     */
    public void updateAuthor(Author author){
        this.author = author;
    }

    public void setAuthor(Author author) {
        this.author = author;
        author.getBooks().add(this);
    }

    public Author getAuthor(){
        return author != null ? author : Author.UNKNOWN_AUTHOR;
    }

    public boolean giveOut(Person person){
        if (isTaken())
            return false;

        setTaker(person);
        person.addTakenBook(this);
        return true;
    }

    public void giveBack(){
        taker.getTakenBooks().remove(this);
        setTaker(null);
    }

    public boolean isTaken(){
        return getTaker() != null;
    }
}
