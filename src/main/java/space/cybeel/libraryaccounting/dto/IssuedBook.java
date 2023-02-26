package space.cybeel.libraryaccounting.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class IssuedBook extends Book {
    @Getter
    @Setter
    private Person issuer;

    @Getter
    @Setter
    private int issuerId;

    public IssuedBook(int year, int authorId, String title, int id, Person issuer) {
        super(year, authorId, title, id);
        this.issuer = issuer;
    }

    public IssuedBook(Book book) {
        super(book.getYear(), book.getAuthorId(), book.getTitle(), book.getId());
    }

    public IssuedBook() {
        super();
    }
}
