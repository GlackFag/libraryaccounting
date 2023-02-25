package space.cybeel.libraryaccounting.util.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import space.cybeel.libraryaccounting.dto.Book;

public class BookValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Book.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        int id = book.getId();
    }
}
