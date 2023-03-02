package space.cybeel.libraryaccounting.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import space.cybeel.libraryaccounting.dao.AuthorDAO;
import space.cybeel.libraryaccounting.dao.BookDAO;
import space.cybeel.libraryaccounting.dto.Author;
import space.cybeel.libraryaccounting.dto.Book;
import space.cybeel.libraryaccounting.util.BookInfoFormer;
import space.cybeel.libraryaccounting.util.validators.AuthorValidator;

import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {
    private final AuthorValidator validator;
    private final AuthorDAO authorDAO;
    private final BookDAO bookDAO;


    @Autowired
    public AuthorController(AuthorValidator validator, AuthorDAO authorDAO, BookDAO bookDAO) {
        this.validator = validator;
        this.authorDAO = authorDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping("/new")
    public String addPage(Model model) {
        model.addAttribute("author", new Author());

        return "author/new";
    }

    @GetMapping("{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        Author author = authorDAO.show(id);
        if (author == null)
            return "404";

        model.addAttribute("author", author);
        return "author/edit";
    }

    @GetMapping({"/", ""})
    public String index(Model model) {
        model.addAttribute("authorList", authorDAO.index());

        return "author/index";
    }

    @PatchMapping({"/", ""})
    public String edit(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult) {
        validate(author, bindingResult);

        if(bindingResult.hasErrors())
            return "author/edit";

        authorDAO.update(author.getId(), author);

        return "redirect:/author";
    }

    @PostMapping({"/", ""})
    public String add(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult) {
        validate(author, bindingResult);
        System.out.println(author.getName());

        if(bindingResult.hasErrors())
            return "author/new";

        authorDAO.save(author);
        return "redirect:/author";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        authorDAO.delete(id);

        return "redirect:/author";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Author author = authorDAO.show(id);
        if (author == null)
            return "404";

        addShowAttributes(author, model);
        return "author/show";
    }


    private void addShowAttributes(Author author, Model model) {
        List<Book> bookList = bookDAO.index();

        bookList.removeIf(x -> x.getAuthorId() != author.getId());
        bookList.forEach(x -> x.setFormedInfo(BookInfoFormer.form(x, author)));

        model.addAttribute("author", author);
        model.addAttribute("bookList", bookList);
    }

    private void validate(Author author, Errors errors) {
        if (errors.hasErrors())
            return;
        validator.validate(author, errors);
    }
}
