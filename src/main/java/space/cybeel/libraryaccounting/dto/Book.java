package space.cybeel.libraryaccounting.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private String title;

    @Setter
    @Getter
    private int id;

    @Getter
    @Setter
    private String takenBy;

    public Book(int year, int authorId, String title, int id, String takenBy) {
        this.year = year;
        this.authorId = authorId;
        this.title = title;
        this.id = id;
        this.takenBy = takenBy;
    }

    public Book() {
    }
}
