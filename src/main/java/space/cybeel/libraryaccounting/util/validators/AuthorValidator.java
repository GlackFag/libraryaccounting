package space.cybeel.libraryaccounting.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import space.cybeel.libraryaccounting.dao.AuthorDAO;
import space.cybeel.libraryaccounting.dto.Author;

@Component
public class AuthorValidator implements Validator {
    private final AuthorDAO authorDAO;

    @Autowired
    public AuthorValidator(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Author.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Author author = (Author) target;

        if(authorDAO.isExists(author))
            errors.rejectValue("name", "already.exists.author.name");
    }
}
