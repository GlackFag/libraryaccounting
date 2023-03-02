package space.cybeel.libraryaccounting.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import space.cybeel.libraryaccounting.dao.BookDAO;
import space.cybeel.libraryaccounting.dto.Book;


@Component
public class BookValidator implements Validator {
    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Book.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (bookDAO.isExists(book))
            errors.rejectValue("title", "already.exists.book.title");

    }
}
