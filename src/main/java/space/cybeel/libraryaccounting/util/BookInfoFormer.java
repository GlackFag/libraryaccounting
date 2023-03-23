package space.cybeel.libraryaccounting.util;

import space.cybeel.libraryaccounting.models.Author;
import space.cybeel.libraryaccounting.models.Book;


public class BookInfoFormer {
    public static String form(Book book, Author author) {
        String title = book.getTitle();
        author = author != null ? author : Author.UNKNOWN_AUTHOR;

        return String.format("%s - %s, %d", author.getName(), title, book.getYear());
    }

    public static String form(Book book){
        return form(book, book.getAuthor());
    }

    private BookInfoFormer() {
    }
}
