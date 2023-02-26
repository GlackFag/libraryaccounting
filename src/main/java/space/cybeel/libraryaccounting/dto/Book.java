package space.cybeel.libraryaccounting.dto;

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
    private Author author;

    @Getter
    @Setter
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

    public Book() {
    }
}
