package space.cybeel.libraryaccounting.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import space.cybeel.libraryaccounting.models.Book;
import space.cybeel.libraryaccounting.models.Person;
import space.cybeel.libraryaccounting.services.AuthorService;
import space.cybeel.libraryaccounting.services.BookService;
import space.cybeel.libraryaccounting.services.PersonService;
import space.cybeel.libraryaccounting.util.BookInfoFormer;
import space.cybeel.libraryaccounting.util.validators.PersonValidator;

import java.util.List;

@Controller
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    private final BookService bookService;

    private final PersonValidator validator;

    @Autowired
    public PersonController(PersonService personService, BookService bookService, PersonValidator validator) {
        this.personService = personService;
        this.bookService = bookService;
        this.validator = validator;
    }

    @GetMapping("/new")
    public String addPage(Model model) {
        model.addAttribute("person", new Person());

        return "person/new";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        Person person = personService.findOne(id);
        if (person == null)
            return "404";

        model.addAttribute("person", person);
        return "person/edit";
    }

    @GetMapping({"/", ""})
    public String index(Model model) {
        List<Person> people = personService.findAll();

        model.addAttribute("personList", people);

        return "person/index";
    }

    @PostMapping({"/", ""})
    public String add(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "person/new";

        personService.save(person);

        return "redirect:/person";
    }

    @PatchMapping("/{id}")
    public String edit(@PathVariable("id") int id,
                       @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "person/edit";

        personService.update(id, person);

        return "redirect:/person";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        personService.deleteById(id);

        return "redirect:/person";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Person person = personService.findOne(id);
        if (person == null)
            return "404";

        addShowAttributes(person, model);
        return "person/show";
    }

    private void addShowAttributes(Person person, Model model) {
        List<Book> issued = bookService.findByTaker(person);
        issued.forEach(x -> x.setFormedInfo(BookInfoFormer.form(x, x.getAuthor())));

        model.addAttribute("issuedBookList", issued);
        model.addAttribute("person", person);
    }

    private void validate(Person person, Errors errors){
        if (errors.hasErrors())
            return;
        validator.validate(person, errors);
    }
}
