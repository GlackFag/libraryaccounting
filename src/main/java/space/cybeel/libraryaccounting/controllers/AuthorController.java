package space.cybeel.libraryaccounting.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import space.cybeel.libraryaccounting.models.Author;
import space.cybeel.libraryaccounting.services.AuthorService;
import space.cybeel.libraryaccounting.util.validators.AuthorValidator;

@Controller
@RequestMapping("/author")
public class AuthorController {
    private final AuthorValidator validator;

    private final AuthorService authorService;


    @Autowired
    public AuthorController(AuthorValidator validator, AuthorService authorService) {
        this.validator = validator;
        this.authorService = authorService;
    }

    @GetMapping("/new")
    public String addPage(Model model) {
        model.addAttribute("author", new Author());

        return "author/new";
    }

    @GetMapping("{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        Author author = authorService.findOne(id);
        if (author == null)
            return "404";

        model.addAttribute("author", author);
        return "author/edit";
    }

    @GetMapping({"/", ""})
    public String index(Model model) {
        model.addAttribute("authorList", authorService.findAll());

        return "author/index";
    }

    @PatchMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       @ModelAttribute("author") @Valid Author author, BindingResult bindingResult) {
        validate(author, bindingResult);

        if(bindingResult.hasErrors()) {
            System.out.println("here");
            return "author/edit";
        }

        authorService.update(id, author);
        return "redirect:/author";
    }

    @PostMapping({"/", ""})
    public String add(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult) {
        validate(author, bindingResult);
        System.out.println(author.getName());

        if(bindingResult.hasErrors())
            return "author/new";

        authorService.save(author);
        return "redirect:/author";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        authorService.deleteById(id);

        return "redirect:/author";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        authorService.addShowAttributes(id, model);
        return "author/show";
    }


    private void validate(Author author, Errors errors) {
        validator.validate(author, errors);
    }
}
