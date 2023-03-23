package space.cybeel.libraryaccounting.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import space.cybeel.libraryaccounting.models.Author;
import space.cybeel.libraryaccounting.models.Book;
import space.cybeel.libraryaccounting.models.Person;
import space.cybeel.libraryaccounting.services.AuthorService;
import space.cybeel.libraryaccounting.services.BookService;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    private final AuthorService authorService;

    private final BookService bookService;


    @Autowired
    public BookController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @PostMapping("{bookId}/giveout")
    public String giveOut(@PathVariable("bookId") int bookId,
                          @ModelAttribute("person") Person person) {
        bookService.giveOut(bookId, person.getId());

        return "redirect:/book";
    }

    @PostMapping("{bookId}/giveback")
    public String giveBack(@PathVariable("bookId") int bookId) {
        bookService.giveBack(bookId);

        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        Book book = bookService.findOne(id);
        book.setAuthor(new Author());

        model.addAttribute("book", book);
        model.addAttribute("authorList", authorService.findAll());
        model.addAttribute("newAuthor", new Author());

        return "book/edit";
    }

    @GetMapping("/new")
    public String addPage(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("newAuthor", new Author());
        model.addAttribute("authorList", authorService.findAll());

        return "book/new";
    }

    @GetMapping({"/", ""})
    public String index(Model model) {
        List<Book> books = bookService.indexList();

        model.addAttribute("bookList", books);

        return "book/index";
    }

    @PatchMapping({"/{id}", ""})
    public String edit(@PathVariable("id") int id,
                       @ModelAttribute("newAuthor") Author author,
                       @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "book/edit";

        book.updateAuthor(author);
        bookService.update(id, book);

        return "redirect:/book";
    }

    @PostMapping({"/", ""})
    public String add(@ModelAttribute("newAuthor") Author author,
                      @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "book/new";

        book.updateAuthor(author);
        bookService.save(book);

        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int bookId) {
        bookService.deleteById(bookId);

        return "redirect:/book";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        bookService.addShowAttributes(id, model);
        return "book/show";
    }

    @ModelAttribute
    public void defaultAttributes(Model model) {
        model.addAttribute("unknownAuthor", Author.UNKNOWN_AUTHOR);
    }
}
