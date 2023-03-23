package space.cybeel.libraryaccounting.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import space.cybeel.libraryaccounting.models.Author;
import space.cybeel.libraryaccounting.services.AuthorService;

@Component
public class AuthorValidator implements Validator {
    private final AuthorService service;

    @Autowired
    public AuthorValidator(AuthorService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Author.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Author author = (Author) target;

        if(!isNameUnique(author.getName()))
            errors.rejectValue("name", null, "Author with such name is already exist");
    }

    private boolean isNameUnique(String name){
      for(Author e : service.findAll())
          if (e.getName().equals(name))
              return false;
      return true;
    }
}
