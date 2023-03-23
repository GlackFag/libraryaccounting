package space.cybeel.libraryaccounting.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "person")
public class Person extends DataTransfetObject{
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Size(min = 5, max = 150, message = "Name should be between 5 and 150")
    @Getter
    @Setter
    @Column(name = "fullName")
    private String fullName;

    @Min(value = 1850, message = "You birth year should be grater or equal 1850")
    @Max(value = 2023, message = "You birth year should be lower or equal 2023")
    @Getter
    @Setter
    @Column(name = "yearBorn")
    private int yearBorn;

    @Getter
    @Setter
    @OneToMany(mappedBy = "taker")
    private List<Book> takenBooks;


    public Person(String fullName, int yearBorn, int id) {
        this.fullName = fullName;
        this.yearBorn = yearBorn;
        this.id = id;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", yearBorn=" + yearBorn +
                '}';
    }

    public void addTakenBook(Book book){
        book.setTaker(this);
        getTakenBooks().add(book);
    }
}
