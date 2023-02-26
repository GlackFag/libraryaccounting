package space.cybeel.libraryaccounting.util;

import space.cybeel.libraryaccounting.dto.Author;
import space.cybeel.libraryaccounting.dto.Book;

import java.util.List;

public class BookInfoFormer {
    public static List<String> form(List<? extends Book> books, DTOList<Author> authors) {
        return books.stream().map(x -> form(x, authors)).toList();
    }

    public static String form(Book book, DTOList<Author> authors) {
        Author author = authors.getByIdOrDefault(book.getAuthorId(), Author.UNKNOWN_AUTHOR);
        return form(book, author);
    }

    public static String form(Book book, Author author) {
        String title = book.getTitle();

        return String.format("%s - %s, %d", author.getName(), title, book.getYear());
    }

    public static List<String> formWithoutAuthor(List<? extends Book> books){
        return books.stream().map(x -> String.format("%s, %d", x.getTitle(), x.getYear())).toList();
    }

    private BookInfoFormer() {
    }
}
